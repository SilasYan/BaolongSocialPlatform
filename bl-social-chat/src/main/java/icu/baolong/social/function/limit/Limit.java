package icu.baolong.social.function.limit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限流注解
 *
 * @author Silas Yan 2025-05-22 22:58
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Limit {

	/**
	 * 限流 KEY
	 */
	String key() default "LIMIT:";

	/**
	 * 限流时间，默认10秒，单位秒
	 */
	long time() default 10L;

	/**
	 * 允许请求的次数，默认5次
	 */
	long count() default 5L;

	/**
	 * 限制类型
	 */
	LimitType limitType() default LimitType.DEFAULT;

	String errMsg() default "请求过于频繁，请稍后再试";
}
