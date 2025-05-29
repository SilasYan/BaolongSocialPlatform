package icu.baolong.social.module.sys.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.sys.entity.SysRole;
import icu.baolong.social.repository.sys.mapper.SysRoleMapper;
import org.springframework.stereotype.Repository;

/**
 * 系统角色表 (sys_role) - 持久化服务
 *
 * @author Baolong 2025-05-29 20:06:21
 */
@Repository
public class SysRoleDao extends ServiceImpl<SysRoleMapper, SysRole> {
}
