package icu.baolong.social.module.sys.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.sys.entity.SysPermission;
import icu.baolong.social.repository.sys.mapper.SysPermissionMapper;
import org.springframework.stereotype.Repository;

/**
 * 系统权限表 (sys_permission) - 持久化服务
 *
 * @author Baolong 2025-05-29 20:54:40
 */
@Repository
public class SysPermissionDao extends ServiceImpl<SysPermissionMapper, SysPermission> {
}
