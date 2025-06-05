package icu.baolong.social.function.transaction;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import icu.baolong.social.repository.message.entity.MessageInvokeRecord;
import icu.baolong.social.repository.message.entity.SnapshotParam;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * 安全执行注解切面类
 *
 * @author Silas Yan 2025-06-04 21:27
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 1) // 最高优先级
@Aspect
@Component
public class SecureInvokeAspect {

	@Resource
	private SecureInvokeService secureInvokeService;

	@Around("@annotation(secureInvoke)")
	public Object around(ProceedingJoinPoint joinPoint, SecureInvoke secureInvoke) throws Throwable {
		boolean isAsync = secureInvoke.isAsync();
		boolean inTransaction = TransactionSynchronizationManager.isActualTransactionActive();
		// 非事务中直接执行
		if (SecureInvokeHolder.get() || !inTransaction) {
			return joinPoint.proceed();
		}
		// 获取到方法
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		// 获取到参数类型
		List<String> parameters = Stream.of(method.getParameterTypes()).map(Class::getName).toList();
		// 构建快照参数
		SnapshotParam snapshotParam = SnapshotParam.builder()
				.className(method.getDeclaringClass().getName())
				.methodName(method.getName())
				.params(JSONUtil.toJsonStr(joinPoint.getArgs()))
				.paramTypes(JSONUtil.toJsonStr(parameters))
				.build();
		// 创建消息执行记录
		MessageInvokeRecord messageInvokeRecord = new MessageInvokeRecord()
				.setSnapshotParam(snapshotParam)
				.setMaxRetryCount(secureInvoke.maxRetryCount())
				.setNextRetryTime(DateUtil.offsetMinute(new Date(), 2));
		// 执行
		secureInvokeService.execute(messageInvokeRecord, isAsync);
		return null;
	}
}
