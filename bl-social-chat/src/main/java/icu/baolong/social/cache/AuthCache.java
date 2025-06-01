package icu.baolong.social.cache;

import cn.hutool.core.collection.CollUtil;
import icu.baolong.social.auth.dto.AuthDTO;
import icu.baolong.social.common.utils.RedisUtil;
import icu.baolong.social.common.constants.KeyConstant;
import icu.baolong.social.module.sys.dao.RolePermissionDao;
import icu.baolong.social.module.sys.dao.SysPermissionDao;
import icu.baolong.social.module.sys.dao.SysRoleDao;
import icu.baolong.social.module.user.dao.UserRoleDao;
import icu.baolong.social.repository.sys.entity.RolePermission;
import icu.baolong.social.repository.sys.entity.SysPermission;
import icu.baolong.social.repository.sys.entity.SysRole;
import icu.baolong.social.repository.user.entity.UserRole;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 权限缓存
 *
 * @author Silas Yan 2025-05-29 21:06
 */
@Slf4j
@Component
public class AuthCache {

	@Resource
	private RedisUtil redisUtil;
	@Resource
	private UserRoleDao userRoleDao;
	@Resource
	private SysRoleDao sysRoleDao;
	@Resource
	private RolePermissionDao rolePermissionDao;
	@Resource
	private SysPermissionDao sysPermissionDao;

	/**
	 * 获取用户的权限
	 *
	 * @return 用户的权限
	 */
	public AuthDTO getUserAuth(Long userId) {
		String key = String.format(KeyConstant.PREFIX_USER_AUTH, userId);
		Object o = redisUtil.get(key);
		if (o != null) {
			log.info("[权限缓存]命中缓存: {}", key);
			return (AuthDTO) o;
		}
		// 获取当前用户所有的角色
		List<UserRole> userRoles = userRoleDao.getUserRoleListByUserId(userId);
		if (CollUtil.isEmpty(userRoles)) {
			return AuthDTO.builder().build();
		}
		// 获取所有的角色
		Set<Long> roleIdList = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
		List<SysRole> roleList = sysRoleDao.listByIds(roleIdList);
		if (CollUtil.isEmpty(roleList)) {
			return AuthDTO.builder().build();
		}
		Set<String> roleSignList = roleList.stream().map(SysRole::getRoleSign).collect(Collectors.toSet());
		// 获取所有的权限
		List<RolePermission> rolePermissions = rolePermissionDao.getPermListByRoleIds(roleIdList);
		Set<Long> permIdList = rolePermissions.stream().map(RolePermission::getPermId).collect(Collectors.toSet());
		List<SysPermission> permissionList = sysPermissionDao.listByIds(permIdList);
		Set<String> permSignList = permissionList.stream().map(SysPermission::getPermSign).collect(Collectors.toSet());
		// 组装树状结构
		Map<Long, List<RolePermission>> rolePermMap = rolePermissions.stream().collect(Collectors.groupingBy(RolePermission::getRoleId));
		Map<Long, String> permMap = permissionList.stream().collect(Collectors.toMap(SysPermission::getPermId, SysPermission::getPermSign));
		List<AuthDTO> roleTree = new ArrayList<>();
		roleList.forEach(role -> {
			List<RolePermission> treeRolePermList = rolePermMap.get(role.getRoleId());
			List<AuthDTO> permTree = new ArrayList<>();
			for (RolePermission permission : treeRolePermList) {
				permTree.add(AuthDTO.builder()
						.permId(permission.getPermId())
						.permSign(permMap.get(permission.getPermId()))
						.build());
			}
			roleTree.add(AuthDTO.builder()
					.roleId(role.getRoleId())
					.roleSign(role.getRoleSign())
					.permTree(permTree)
					.build());
		});
		// 组装数据对象
		AuthDTO build = AuthDTO.builder()
				.roleIdList(roleIdList)
				.roleSignList(roleSignList)
				.permIdList(permIdList)
				.permSignList(permSignList)
				.roleTree(roleTree)
				.build();
		log.info("[权限缓存]未命中缓存: {}", key);
		redisUtil.set(key, build, 7, TimeUnit.DAYS);
		return build;
	}
}
