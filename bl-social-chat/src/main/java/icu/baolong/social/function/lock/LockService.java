package icu.baolong.social.function.lock;

import icu.baolong.social.common.exception.BusinessException;
import icu.baolong.social.base.response.RespCode;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 分布式锁工具
 *
 * @author Silas Yan 2025-05-25 20:58
 */
@Component
public class LockService {

	@Resource
	private RedissonClient redissonClient;

	/**
	 * 通用分布式锁
	 *
	 * @param key      锁标识
	 * @param waitTime 等待获取锁的时间
	 * @param unit     时间单位
	 * @param supplier 需要执行的业务逻辑, 函数时接口
	 * @param <T>      泛型
	 * @return 泛型
	 */
	@SneakyThrows
	public <T> T executeWithLock(String key, long waitTime, TimeUnit unit, SupplierThrow<T> supplier) {
		RLock lock = redissonClient.getLock(key);
		try {
			boolean result = lock.tryLock(waitTime, unit);
			if (!result) {
				throw new BusinessException(RespCode.REQUEST_LIMIT);
			}
			return supplier.get();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 通用分布式锁
	 *
	 * @param key      锁标识
	 * @param supplier 需要执行的业务逻辑, 函数时式口
	 * @param <T>      泛型
	 * @return 泛型
	 */
	@SneakyThrows
	public <T> T executeWithLock(String key, Supplier<T> supplier) {
		return executeWithLock(key, -1, TimeUnit.MILLISECONDS, supplier::get);
	}

	/**
	 * 通用分布式锁
	 *
	 * @param key      锁标识
	 * @param runnable 需要执行的业务逻辑, 无返回值的函数时式口
	 * @param <T>      泛型
	 * @return 泛型
	 */
	@SneakyThrows
	public <T> T executeWithLock(String key, Runnable runnable) {
		return executeWithLock(key, -1, TimeUnit.MILLISECONDS, () -> {
			runnable.run();
			return null;
		});
	}

	/**
	 * 定义函数式接口
	 *
	 * @param <T> 泛型
	 */
	@FunctionalInterface
	public interface SupplierThrow<T> {
		T get() throws Throwable;
	}
}
