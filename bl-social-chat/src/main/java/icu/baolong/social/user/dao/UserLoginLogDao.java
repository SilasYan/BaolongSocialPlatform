package icu.baolong.social.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.user.domain.entity.UserLoginLog;
import icu.baolong.social.user.mapper.UserLoginLogMapper;
import icu.baolong.social.user.service.UserLoginLogService;
import org.springframework.stereotype.Service;

/**
 * 用户登录日志表 (user_login_log) - 持久化服务
 *
 * @author Baolong 2025-05-21 21:01:07
 */
@Service
public class UserLoginLogDao extends ServiceImpl<UserLoginLogMapper, UserLoginLog> {
}
