package icu.baolong.social.base.config;

import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sa-Token配置类
 *
 * @author Silas Yan 2025-05-22 22:28
 */
@Configuration
public class SaTokenConfig {

	/**
	 * Sa-Token 整合 jwt (Simple 简单模式)
	 *
	 * @return StpLogic
	 */
	@Bean
	public StpLogic getStpLogicJwt() {
		return new StpLogicJwtForSimple();
	}
}
