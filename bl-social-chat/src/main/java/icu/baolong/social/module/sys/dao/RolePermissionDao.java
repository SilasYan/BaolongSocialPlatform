package icu.baolong.social.module.sys.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.sys.entity.RolePermission;
import icu.baolong.social.repository.sys.mapper.RolePermissionMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 角色权限关联表 (role_permission) - 持久化服务
 *
 * @author Baolong 2025-05-29 20:54:40
 */
@Repository
public class RolePermissionDao extends ServiceImpl<RolePermissionMapper, RolePermission> {

	/**
	 * 根据角色列表获取权限列表
	 *
	 * @param roleIds 角色列表
	 * @return 权限列表
	 */
	public List<RolePermission> getPermListByRoleIds(Set<Long> roleIds) {
		return this.lambdaQuery().in(RolePermission::getRoleId, roleIds).list();
	}
}
