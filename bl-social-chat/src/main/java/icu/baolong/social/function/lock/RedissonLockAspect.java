package icu.baolong.social.function.lock;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 分布式锁切面
 *
 * @author Silas Yan 2025-05-25 21:29
 */
@Order(0) // 保证该AOP在@Transactional之前执行
@Aspect
@Component
public class RedissonLockAspect {

	@Resource
	private LockService lockService;

	@Around("@annotation(redissonLock)")
	public Object aroundRedisLock(ProceedingJoinPoint point, RedissonLock redissonLock) {
		Object[] args = point.getArgs();
		MethodSignature methodSignature = (MethodSignature) point.getSignature();
		Method method = methodSignature.getMethod();

		// 获取锁前缀
		String prefix = redissonLock.prefix();
		// 默认获取方法名作为锁前缀
		String keyPrefix = StrUtil.isBlank(prefix) ? RedissonLockAspect.getMethodKey(method) : prefix;

		// 获取锁标识
		String key = RedissonLockAspect.parseSpEl(methodSignature.getMethod(), args, redissonLock.key());

		// 执行锁等操作
		return lockService.executeWithLock(keyPrefix + ":" + key, redissonLock.waitTime(), redissonLock.unit(), point::proceed);
	}

	private static final ExpressionParser PARSER = new SpelExpressionParser();

	private static final DefaultParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

	public static String getMethodKey(Method method) {
		return method.getDeclaringClass() + "#" + method.getName();
	}

	public static String parseSpEl(Method method, Object[] args, String spEl) {
		// 解析参数名
		String[] params = Optional.ofNullable(PARAMETER_NAME_DISCOVERER.getParameterNames(method)).orElse(new String[]{});
		// el解析需要的上下文对象
		EvaluationContext context = new StandardEvaluationContext();
		for (int i = 0; i < params.length; i++) {
			// 所有参数都作为原材料扔进去
			context.setVariable(params[i], args[i]);
		}
		Expression expression = PARSER.parseExpression(spEl);
		return expression.getValue(context, String.class);
	}
}
