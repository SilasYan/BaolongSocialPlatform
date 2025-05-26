package icu.baolong.social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 启动类
 *
 * @author Silas Yan 2025-05-20 20:46
 */
@EnableAspectJAutoProxy(exposeProxy = true) // 开启AOP代理, 使用 AopContext.currentProxy() 时需要开启
@SpringBootApplication
public class BaolongSocialApplication {
	public static void main(String[] args) {
		SpringApplication.run(BaolongSocialApplication.class, args);
	}
}
