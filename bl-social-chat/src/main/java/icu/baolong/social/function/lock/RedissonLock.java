package icu.baolong.social.function.lock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁注解
 *
 * @author Silas Yan 2025-05-25 21:27
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedissonLock {

	/**
	 * 前缀
	 */
	String prefix() default "";

	/**
	 * KEY, 支持 EL 表达式
	 */
	String key();

	/**
	 * 锁等待时间，默认-1表示直接失败
	 */
	long waitTime() default -1;

	/**
	 * 时间单位，默认毫秒
	 */
	TimeUnit unit() default TimeUnit.MILLISECONDS;
}
