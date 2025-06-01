package icu.baolong.social.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import icu.baolong.social.cache.UserCache;
import icu.baolong.social.common.utils.ServletUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

/**
 * 黑名单拦截器
 *
 * @author Silas Yan 2025-05-29 23:26
 */
@Slf4j
@Order(1)
@Component
public class BlacklistInterceptor implements HandlerInterceptor {

	@Resource
	private UserCache userCache;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 获取请求IP
		String requestIp = ServletUtil.getIp(request);
		// 获取当前登录的用户ID
		Long userId = null;
		if (StpUtil.isLogin()) {
			userId = StpUtil.getLoginIdAsLong();
		}
		// 获取黑名单
		Set<String> blacklist = userCache.getBlacklist();
		if (StrUtil.isBlank(requestIp) || ObjectUtil.isNull(userId) || userId <= 0L || CollUtil.isEmpty(blacklist)) {
			return true;
		}
		if (blacklist.contains(requestIp) || blacklist.contains(String.valueOf(userId))) {
			log.info("[黑名单拦截器]IP: {}, userId: {}", requestIp, userId);
			response.setStatus(403);
			response.getWriter().write("您已被拉入黑名单");
			return false;
		}
		return true;
	}
}
