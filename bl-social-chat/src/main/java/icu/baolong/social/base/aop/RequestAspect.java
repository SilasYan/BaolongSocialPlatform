package icu.baolong.social.base.aop;

import cn.hutool.json.JSONUtil;
import icu.baolong.social.common.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 请求切面类
 *
 * @author Silas Yan 2025-04-03:20:16
 */
@Slf4j
@Order(-1)
@Aspect
@Component
public class RequestAspect {

	// 定义切入点表达式
	private static final String POINT_CUT = "@within(org.springframework.web.bind.annotation.RestController)" +
			" || @within(org.springframework.stereotype.Controller)";

	// 使用该注解定义切入点
	@Pointcut(POINT_CUT)
	public void requestLog() {
	}

	/**
	 * 环绕通知：记录请求基本信息与执行耗时
	 */
	@Around("requestLog()")
	public Object doAfter(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (requestAttributes == null) {
			return joinPoint.proceed(joinPoint.getArgs());
		}
		HttpServletRequest request = requestAttributes.getRequest();

		log.info("---------- 请求日志 ----------");
		log.info("IP 地址: {}", ServletUtil.getIp(request));
		log.info("请求接口: [{}] {}", request.getMethod(), request.getRequestURI());
		log.info("请求方法: {}.{}", joinPoint.getSignature().getDeclaringType().getSimpleName(), joinPoint.getSignature().getName());
		log.info("请求参数: {}", JSONUtil.toJsonStr(filterArgs(joinPoint.getArgs())));

		long start = System.currentTimeMillis();
		try {
			Object result = joinPoint.proceed(joinPoint.getArgs());
			long end = System.currentTimeMillis();
			log.info("接口耗时: {}ms", end - start);
			return result;
		} finally {
			// 不管是否抛出异常，都输出日志结束标识
			log.info("--------------------------");
		}
	}

	/**
	 * 异常通知：当目标方法抛出异常时触发
	 */
	@AfterThrowing(pointcut = "requestLog()", throwing = "ex")
	public void doAfterThrowing(Throwable ex) {
		log.info("请求异常: {}", ex.getMessage());
	}

	/**
	 * 过滤掉不能序列化的参数类型
	 */
	private List<Object> filterArgs(Object[] objects) {
		return Arrays.stream(objects)
				.filter(obj -> !(obj instanceof MultipartFile)
						&& !(obj instanceof HttpServletResponse)
						&& !(obj instanceof HttpServletRequest))
				.collect(Collectors.toList());
	}
}
