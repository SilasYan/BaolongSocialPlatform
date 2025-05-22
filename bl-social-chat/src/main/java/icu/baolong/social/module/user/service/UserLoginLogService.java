package icu.baolong.social.module.user.service;

import java.util.Date;

/**
 * 用户登录日志表 (user_login_log) - 服务接口
 *
 * @author Baolong 2025-05-21 21:01:07
 */
public interface UserLoginLogService {

	/**
	 * 记录用户登录日志
	 *
	 * @param userId 用户ID
	 * @param date   登录时间
	 * @param ip     登录IP
	 * @param header 登录头
	 */
	void recordLoginLog(Long userId, Date date, String ip, String header);
}
