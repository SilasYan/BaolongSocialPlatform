package icu.baolong.social.module.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.user.entity.UserLoginLog;
import icu.baolong.social.repository.user.mapper.UserLoginLogMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户登录日志表 (user_login_log) - 持久化服务
 *
 * @author Baolong 2025-05-21 21:01:07
 */
@Repository
public class UserLoginLogDao extends ServiceImpl<UserLoginLogMapper, UserLoginLog> {
}
