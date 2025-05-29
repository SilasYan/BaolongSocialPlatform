package icu.baolong.social.common.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import icu.baolong.social.common.interceptor.BlacklistInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * @author Silas Yan 2025-05-29 23:25
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Resource
	private BlacklistInterceptor blacklistInterceptor;

	/**
	 * 注册拦截器
	 *
	 * @param registry InterceptorRegistry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册黑名单拦截器
		registry.addInterceptor(blacklistInterceptor);
		// 注册Sa-Token拦截器
		registry.addInterceptor(new SaInterceptor(handler -> StpUtil.checkLogin()))
				.addPathPatterns("/api/**")
				.excludePathPatterns("/api/user/emailCode", "/api/user/register"
						, "/api/user/graphicCode", "/api/user/login");
	}
}
