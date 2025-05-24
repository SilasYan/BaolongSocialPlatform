package icu.baolong.social.module.user.service.impl;

import icu.baolong.social.common.thread.ThreadPoolConfig;
import icu.baolong.social.module.user.dao.UserLoginLogDao;
import icu.baolong.social.module.user.service.UserLoginLogService;
import icu.baolong.social.repository.user.entity.UserLoginLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户登录日志表 (user_login_log) - 服务实现
 *
 * @author Baolong 2025-05-21 21:01:07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserLoginLogServiceImpl implements UserLoginLogService {

	private final UserLoginLogDao userLoginLogDao;

	/**
	 * 记录用户登录日志
	 *
	 * @param userId 用户ID
	 * @param date   登录时间
	 * @param ip     登录IP
	 * @param header 登录头
	 */
	@Async(ThreadPoolConfig.COMMON_EXECUTOR)
	@Override
	public void recordLoginLog(Long userId, Date date, String ip, String header) {
		UserLoginLog userLoginLog = new UserLoginLog()
				.setUserId(userId)
				.setLoginIp(ip)
				.setLoginTime(date)
				.setUserAgent(header);
		userLoginLogDao.save(userLoginLog);
		log.info("[登录日志] 用户 [{}] 登录成功!", userId);
	}
}
