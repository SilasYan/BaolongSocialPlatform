package icu.baolong.social.function.transaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 安全执行注解（保证方法执行成功，如果在事务内的方法会将操作计入到数据库，保证一定会执行）
 *
 * @author Silas Yan 2025-06-04 21:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SecureInvoke {

	/**
	 * 最大重试次数
	 *
	 * @return 重试次数, 默认3次
	 */
	int maxRetryCount() default 3;

	/**
	 * 是否异步执行（默认异步执行: 先入库后执行）
	 *
	 * @return 是否异步执行
	 */
	boolean isAsync() default true;
}
