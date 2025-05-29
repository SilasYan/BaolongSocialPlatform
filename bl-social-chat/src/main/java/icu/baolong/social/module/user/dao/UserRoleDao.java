package icu.baolong.social.module.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.user.entity.UserRole;
import icu.baolong.social.repository.user.mapper.UserRoleMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色关联表 (user_role) - 持久化服务
 *
 * @author Baolong 2025-05-29 20:06:21
 */
@Repository
public class UserRoleDao extends ServiceImpl<UserRoleMapper, UserRole> {

	/**
	 * 根据用户ID获取用户角色列表
	 *
	 * @param userId 用户ID
	 * @return 用户角色列表
	 */
	public List<UserRole> getUserRoleListByUserId(Long userId) {
		return this.lambdaQuery().eq(UserRole::getUserId, userId).list();
	}
}
