package icu.baolong.social.common.function.limit;

import icu.baolong.social.common.exception.BusinessException;
import icu.baolong.social.common.response.RespCode;
import icu.baolong.social.common.utils.ServletUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Collections;

/**
 * 限流切面
 *
 * @author Silas Yan 2025-05-22 22:59
 */
@Slf4j
@Aspect
@Component
public class LimitAspect {

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	// 限流脚本
	private static final String LIMIT_SCRIPT_CONTENT = """
			local key = KEYS[1]
			local count = tonumber(ARGV[1])
			local time = tonumber(ARGV[2])
			local current = redis.call('get', key)
			if current and tonumber(current) > count then
				return tonumber(current)
			end
			current = redis.call('incr', key)
			if tonumber(current) == 1 then
				redis.call('expire', key, time)
			end
			return tonumber(current)
			""";
	private static final RedisScript<Long> LIMIT_SCRIPT = new DefaultRedisScript<>(LIMIT_SCRIPT_CONTENT, Long.class);

	@Before("@annotation(limit)")
	public void doBefore(JoinPoint point, Limit limit) {
		String combineKey = getCombineKey(limit, point);
		Long time = limit.time();
		Long count = limit.count();
		Long number = stringRedisTemplate.execute(LIMIT_SCRIPT,
				Collections.singletonList(combineKey), String.valueOf(count), String.valueOf(time));
		if (number == null || number.intValue() > count) {
			log.error("[限流] 请求被限制，已超过 {} 次/{}秒，KEY: [{}]", count, time, combineKey);
			throw new BusinessException(RespCode.REQUEST_LIMIT, limit.errMsg());
		}

	}

	public String getCombineKey(Limit limit, JoinPoint point) {
		StringBuilder sb = new StringBuilder();
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Class<?> targetClass = method.getDeclaringClass();

		if (limit.limitType() == LimitType.GLOBAL) {
			sb.append(targetClass.getName()).append(":").append(method.getName());
		} else if (limit.limitType() == LimitType.CLASS_METHOD_IP || limit.limitType() == LimitType.CLASS_METHOD_UID) {
			sb.append(targetClass.getName()).append(":").append(method.getName()).append(":");
		} else {
			sb.append(limit.key());
		}

		if (limit.limitType() == LimitType.DEFAULT || limit.limitType() == LimitType.IP
				|| limit.limitType() == LimitType.CLASS_METHOD_IP) {
			String ip = getClientIp();
			sb.append(ip);
		} else if (limit.limitType() == LimitType.UID || limit.limitType() == LimitType.CLASS_METHOD_UID) {
			String uid = getCurrentUserId();
			if (uid != null) {
				sb.append(uid);
			} else {
				sb.append("anonymous");
			}
		}

		return sb.toString();
	}

	private String getClientIp() {
		try {
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (attributes != null) {
				return ServletUtil.getRemoteAddr();
			}
		} catch (Exception ignored) {
		}
		return "unknown";
	}

	private String getCurrentUserId() {
		// TODO 用户 ID
		return null;
	}
}
