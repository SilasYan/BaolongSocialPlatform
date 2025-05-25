package icu.baolong.social.common.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import icu.baolong.social.common.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token配置类
 *
 * @author Silas Yan 2025-05-22 22:28
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

	/**
	 * Sa-Token 整合 jwt (Simple 简单模式)
	 *
	 * @return StpLogic
	 */
	@Bean
	public StpLogic getStpLogicJwt() {
		return new StpLogicJwtForSimple();
	}

	/**
	 * 注册拦截器
	 *
	 * @param registry InterceptorRegistry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SaInterceptor(handler -> StpUtil.checkLogin()))
				.addPathPatterns("/api/**")
				.excludePathPatterns("/api/user/emailCode", "/api/user/register"
						, "/api/user/graphicCode", "/api/user/login");
	}
}
