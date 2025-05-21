package icu.baolong.social.common.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 *
 * @author Silas Yan 2025-04-25 20:04
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisUtil {

	@Resource
	private RedisTemplate redisTemplate;

	// region 通用操作

	/**
	 * 设置过期时间
	 *
	 * @param key  键
	 * @param time 时间
	 * @param unit 单位
	 * @return true-成功、false-失败
	 */
	public boolean expire(String key, long time, TimeUnit unit) {
		return Boolean.TRUE.equals(redisTemplate.expire(key, time, unit));
	}

	/**
	 * 设置过期时间
	 *
	 * @param key  键
	 * @param time 时间（秒）
	 * @return true-成功、false-失败
	 */
	public boolean expire(String key, long time) {
		return Boolean.TRUE.equals(redisTemplate.expire(key, time, TimeUnit.SECONDS));
	}

	/**
	 * 获取过期时间
	 *
	 * @param key  键
	 * @param unit 单位
	 * @return 时间; -1 表示永久
	 */
	public long getExpire(String key, TimeUnit unit) {
		Long expire = redisTemplate.getExpire(key, unit);
		return expire != null ? expire : -1;
	}

	/**
	 * 获取过期时间
	 *
	 * @param key 键
	 * @return 时间（秒）; -1 表示永久
	 */
	public long getExpire(String key) {
		return getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 判断是否存在
	 *
	 * @param key 键
	 * @return true-存在、false-不存在
	 */
	public boolean hasKey(String key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

	/**
	 * 删除单个缓存
	 *
	 * @param key 键
	 * @return true-成功、false-失败
	 */
	public boolean delete(String key) {
		return Boolean.TRUE.equals(redisTemplate.delete(key));
	}

	/**
	 * 删除多个缓存
	 *
	 * @param keys 键集合
	 * @return 删除的个数
	 */
	public long delete(Collection<String> keys) {
		Long count = redisTemplate.delete(keys);
		return count != null ? count : 0;
	}

	/**
	 * 获取缓存键
	 *
	 * @param prefix 前缀
	 * @return 键集合
	 */
	public Set<String> keys(String prefix) {
		return redisTemplate.keys(prefix + "*");
	}

	// endregion

	// region String 操作

	/**
	 * 设置缓存
	 *
	 * @param key   键
	 * @param value 内容
	 * @param <T>   泛型
	 */
	public <T> void set(final String key, final T value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 设置缓存
	 *
	 * @param key   键
	 * @param value 内容
	 * @param time  过期时间（秒）
	 * @param <T>   泛型
	 */
	public <T> void set(final String key, final T value, final long time) {
		redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
	}

	/**
	 * 设置缓存
	 *
	 * @param key   键
	 * @param value 内容
	 * @param time  过期时间
	 * @param unit  单位
	 * @param <T>   泛型
	 */
	public <T> void set(final String key, final T value, final long time, final TimeUnit unit) {
		redisTemplate.opsForValue().set(key, value, time, unit);
	}

	/**
	 * 设置缓存（不存在则设置）
	 *
	 * @param key   键
	 * @param value 内容
	 * @param <T>   泛型
	 */
	public <T> void setIfAbsent(final String key, final T value) {
		redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	/**
	 * 批量设置缓存
	 *
	 * @param map 键值
	 * @param <T> 泛型
	 */
	public <T> void multiSet(final Map<String, T> map) {
		redisTemplate.opsForValue().multiSet(map);
	}

	/**
	 * 递增
	 *
	 * @param key   键
	 * @param delta 递增因子
	 * @return 递增后的值; -1表示递增失败
	 */
	public long increment(final String key, final long delta) {
		if (delta <= 0) {
			throw new RuntimeException("递增因子必须大于 0");
		}
		Long increment = redisTemplate.opsForValue().increment(key, delta);
		return increment != null ? increment : -1;
	}

	/**
	 * 递减
	 *
	 * @param key   键
	 * @param delta 递增因子
	 * @return 递减后的值; -1表示递减失败
	 */
	public long decrement(final String key, final long delta) {
		if (delta <= 0) {
			throw new RuntimeException("递减因子必须大于 0");
		}
		Long increment = redisTemplate.opsForValue().increment(key, -delta);
		return increment != null ? increment : -1;
	}

	/**
	 * 获取缓存
	 *
	 * @param key 键
	 * @param <T> 泛型
	 * @return 值（任意类型）
	 */
	public <T> T get(final String key) {
		ValueOperations<String, T> ops = redisTemplate.opsForValue();
		return ops.get(key);
	}

	// endregion

	// region Hash 操作对象

	/**
	 * 设置 Hash 缓存
	 *
	 * @param key 键
	 * @param map Map
	 * @param <T> 泛型
	 */
	public <T> void hmSet(final String key, Map<String, T> map) {
		redisTemplate.opsForHash().putAll(key, map);
	}

	/**
	 * 设置 Hash 缓存
	 *
	 * @param key  键
	 * @param map  Map
	 * @param time 过期时间（秒）
	 * @param <T>  泛型
	 */
	public <T> void hmSet(final String key, Map<String, T> map, final long time) {
		redisTemplate.opsForHash().putAll(key, map);
		redisTemplate.expire(key, time, TimeUnit.SECONDS);
	}

	/**
	 * 设置 Hash 缓存
	 *
	 * @param key  键
	 * @param map  Map
	 * @param time 过期时间
	 * @param unit 单位
	 * @param <T>  泛型
	 */
	public <T> void hmSet(final String key, Map<String, T> map, final long time, final TimeUnit unit) {
		redisTemplate.opsForHash().putAll(key, map);
		redisTemplate.expire(key, time, unit);
	}

	/**
	 * 设置 Hash 对应项（不存在会自动创建）
	 *
	 * @param key   键
	 * @param item  项
	 * @param value 值
	 * @param <T>   泛型
	 */
	public <T> void hSet(final String key, final String item, final Object value) {
		redisTemplate.opsForHash().put(key, item, value);
	}

	/**
	 * 删除 Hash 对应项（可删除多个）
	 *
	 * @param key   键
	 * @param items 项集合
	 * @return 删除的个数
	 */
	public long hDelete(final String key, final Object... items) {
		return redisTemplate.opsForHash().delete(key, items);
	}

	/**
	 * Hash 递增（long）
	 *
	 * @param key   键
	 * @param item  项
	 * @param delta 递增因子
	 * @return 递增后的值
	 */
	public long hIncrement(final String key, final String item, final long delta) {
		if (delta <= 0) {
			throw new RuntimeException("递增因子必须大于 0");
		}
		return redisTemplate.opsForHash().increment(key, item, delta);
	}

	/**
	 * Hash 递减（long）
	 *
	 * @param key   键
	 * @param item  项
	 * @param delta 递减因子
	 * @return 递减后的值
	 */
	public long hDecrement(final String key, final String item, final long delta) {
		if (delta <= 0) {
			throw new RuntimeException("递减因子必须大于 0");
		}
		return redisTemplate.opsForHash().increment(key, item, -delta);
	}

	/**
	 * Hash 递增（double）
	 *
	 * @param key   键
	 * @param item  项
	 * @param delta 递增因子
	 * @return 递增后的值
	 */
	public double hIncrement(final String key, final String item, final double delta) {
		if (delta <= 0) {
			throw new RuntimeException("递增因子必须大于 0.0");
		}
		return redisTemplate.opsForHash().increment(key, item, delta);
	}

	/**
	 * Hash 递减（double）
	 *
	 * @param key   键
	 * @param item  项
	 * @param delta 递减因子
	 * @return 递减后的值
	 */
	public double hDecrement(final String key, final String item, final double delta) {
		if (delta <= 0) {
			throw new RuntimeException("递减因子必须大于 0.0");
		}
		return redisTemplate.opsForHash().increment(key, item, -delta);
	}

	/**
	 * Hash 是否存在项
	 *
	 * @param key  键
	 * @param item 项
	 * @return true-存在、false-不存在
	 */
	public boolean hHasKey(final String key, final String item) {
		return redisTemplate.opsForHash().hasKey(key, item);
	}

	/**
	 * 获取 Hash 所有项值
	 *
	 * @param key 键
	 * @param <T> 泛型
	 * @return 项值集合
	 */
	public <T> Map<String, T> hmGet(final String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 获取 Hash 值
	 *
	 * @param key  键
	 * @param item 项
	 * @param <T>  泛型
	 * @return 值
	 */
	public <T> T hGet(final String key, final String item) {
		HashOperations<String, String, T> ops = redisTemplate.opsForHash();
		return ops.get(key, item);
	}

	// endregion

	// region Set 操作对象

	/**
	 * 设置 Set 缓存
	 *
	 * @param key 键
	 * @param set 集合
	 * @param <T> 泛型
	 * @return 集合数量
	 */
	public <T> long sSet(final String key, final Set<T> set) {
		Long count = redisTemplate.opsForSet().add(key, set);
		return count == null ? 0 : count;
	}

	/**
	 * 设置 Set 缓存
	 *
	 * @param key  键
	 * @param set  集合
	 * @param time 过期时间（秒）
	 * @param <T>  泛型
	 * @return 集合数量
	 */
	public <T> long sSet(final String key, final Set<T> set, final long time) {
		Long count = redisTemplate.opsForSet().add(key, set);
		redisTemplate.expire(key, time, TimeUnit.SECONDS);
		return count == null ? 0 : count;
	}

	/**
	 * 设置 Set 缓存
	 *
	 * @param key  键
	 * @param set  集合
	 * @param time 过期时间
	 * @param unit 单位
	 * @param <T>  泛型
	 * @return 集合数量
	 */
	public <T> long sSet(final String key, final Set<T> set, final long time, final TimeUnit unit) {
		Long count = redisTemplate.opsForSet().add(key, set);
		redisTemplate.expire(key, time, unit);
		return count == null ? 0 : count;
	}

	/**
	 * 设置 Set 缓存
	 *
	 * @param key   键
	 * @param value 值
	 * @param <T>   泛型
	 * @return 集合数量
	 */
	public <T> long sSet(final String key, final T value) {
		Long count = redisTemplate.opsForSet().add(key, value);
		return count == null ? 0 : count;
	}

	/**
	 * 设置 Set 缓存
	 *
	 * @param key    键
	 * @param time   过期时间（秒）
	 * @param values 值集合
	 * @param <T>    泛型
	 * @return 集合数量
	 */
	public <T> long sSet(final String key, final long time, final T... values) {
		Long count = redisTemplate.opsForSet().add(key, values);
		redisTemplate.expire(key, time, TimeUnit.SECONDS);
		return count == null ? 0 : count;
	}

	/**
	 * 设置 Set 缓存
	 *
	 * @param key    键
	 * @param time   过期时间
	 * @param unit   单位
	 * @param values 值集合
	 * @param <T>    泛型
	 * @return 集合数量
	 */
	public <T> long sSet(final String key, final long time, final TimeUnit unit, final T... values) {
		Long count = redisTemplate.opsForSet().add(key, values);
		redisTemplate.expire(key, time, unit);
		return count == null ? 0 : count;
	}

	/**
	 * 删除 Set 的值
	 *
	 * @param key    键
	 * @param values 值集合
	 * @return 删除的数量
	 */
	public long sDelete(final String key, final Object... values) {
		Long count = redisTemplate.opsForSet().remove(key, values);
		return count == null ? 0 : count;
	}

	/**
	 * Set 是否存在值
	 *
	 * @param key   键
	 * @param value 值
	 * @return true-存在、false-不存在
	 */
	public boolean sHasKey(final String key, final Object value) {
		return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
	}

	/**
	 * 获取 Set 集合大小
	 *
	 * @param key 键
	 * @return 集合大小
	 */
	public long sSize(final String key) {
		Long size = redisTemplate.opsForSet().size(key);
		return size == null ? 0 : size;
	}

	/**
	 * 获取 Set 集合
	 *
	 * @param key 键
	 * @param <T> 泛型
	 * @return Set 集合
	 */
	public <T> Set<T> sGet(final String key) {
		return redisTemplate.opsForSet().members(key);
	}

	/**
	 * 获取 Set 集合中的随机一个值
	 *
	 * @param key 键
	 * @param <T> 泛型
	 * @return Set 集合
	 */
	public <T> T sGetRandom(final String key) {
		SetOperations<String, T> ops = redisTemplate.opsForSet();
		return ops.randomMember(key);
	}

	// endregion

	// region ZSet 操作对象

	/**
	 * 设置 ZSet 缓存
	 *
	 * @param key   键
	 * @param value 值集合
	 * @param score 分数
	 * @return true-成功、false-失败
	 */
	public <T> boolean zsSet(final String key, final T value, final double score) {
		return Boolean.TRUE.equals(redisTemplate.opsForZSet().add(key, value, score));
	}

	/**
	 * 删除 ZSet 的值
	 *
	 * @param key    键
	 * @param values 值集合
	 * @return 删除的数量
	 */
	public long zsDelete(final String key, final Object... values) {
		Long count = redisTemplate.opsForZSet().remove(key, values);
		return count == null ? 0 : count;
	}

	/**
	 * 获取 ZSet 的分数
	 *
	 * @param key   键
	 * @param value 值
	 * @return 分数
	 */
	public double zsScore(final String key, final Object value) {
		Double score = redisTemplate.opsForZSet().score(key, value);
		return score == null ? 0 : score;
	}

	/**
	 * 获取 ZSet 集合
	 *
	 * @param key 键
	 * @param <T> 泛型
	 * @return Set 集合
	 */
	public <T> Set<T> zsGet(final String key) {
		return redisTemplate.opsForZSet().range(key, 0, -1);
	}

	/**
	 * 获取 ZSet 集合
	 *
	 * @param key   键
	 * @param start 开始索引
	 * @param end   结束索引; -1表示到最后即全部
	 * @param <T>   泛型
	 * @return Set 集合
	 */
	public <T> Set<T> zsGet(final String key, final long start, final long end) {
		return redisTemplate.opsForZSet().range(key, start, end);
	}

	// endregion

	// region List 操作对象

	/**
	 * 设置 List 缓存（加到最后）
	 *
	 * @param key  键
	 * @param list 集合
	 * @param <T>  泛型
	 * @return 集合数量
	 */
	public <T> long lSet(final String key, final List<T> list) {
		Long count = redisTemplate.opsForList().rightPushAll(key, list);
		return count == null ? 0 : count;
	}

	/**
	 * 设置 List 缓存（加到最后）
	 *
	 * @param key  键
	 * @param list 集合
	 * @param time 过期时间（秒）
	 * @param <T>  泛型
	 * @return 集合数量
	 */
	public <T> long lSet(final String key, final List<T> list, final long time) {
		Long count = redisTemplate.opsForList().rightPushAll(key, list);
		redisTemplate.expire(key, time, TimeUnit.SECONDS);
		return count == null ? 0 : count;
	}

	/**
	 * 设置 List 缓存（加到最后）
	 *
	 * @param key  键
	 * @param list 集合
	 * @param time 过期时间
	 * @param unit 单位
	 * @param <T>  泛型
	 * @return 集合数量
	 */
	public <T> long lSet(final String key, final List<T> list, final long time, final TimeUnit unit) {
		Long count = redisTemplate.opsForList().rightPushAll(key, list);
		redisTemplate.expire(key, time, unit);
		return count == null ? 0 : count;
	}

	/**
	 * 设置 List 缓存（加到最后）
	 *
	 * @param key   键
	 * @param value 值
	 * @param <T>   泛型
	 * @return 集合数量
	 */
	public <T> long lSet(final String key, final T value) {
		Long count = redisTemplate.opsForList().rightPush(key, value);
		return count == null ? 0 : count;
	}

	/**
	 * 设置 List 缓存（加到最前）
	 *
	 * @param key   键
	 * @param value 值
	 * @param <T>   泛型
	 * @return 集合数量
	 */
	public <T> long lSetLeft(final String key, final T value) {
		Long count = redisTemplate.opsForList().leftPush(key, value);
		return count == null ? 0 : count;
	}

	/**
	 * 删除 List 的值
	 *
	 * @param key   键
	 * @param value 值
	 * @param num   数量
	 * @return 删除的数量
	 */
	public long lDelete(final String key, final Object value, final long num) {
		Long count = redisTemplate.opsForList().remove(key, num, value);
		return count == null ? 0 : count;
	}

	/**
	 * 获取 List 集合大小
	 *
	 * @param key 键
	 * @return 集合大小
	 */
	public long lSize(final String key) {
		Long size = redisTemplate.opsForList().size(key);
		return size == null ? 0 : size;
	}

	/**
	 * 获取 List 在某个索引位置的
	 *
	 * @param key   键
	 * @param index 索引
	 * @param <T>   泛型
	 * @return Set 集合
	 */
	public <T> T lIndex(final String key, final long index) {
		ListOperations<String, T> ops = redisTemplate.opsForList();
		return ops.index(key, index);
	}

	/**
	 * 获取 List 集合
	 *
	 * @param key 键
	 * @param <T> 泛型
	 * @return List 集合
	 */
	public <T> List<T> lGet(final String key) {
		return redisTemplate.opsForList().range(key, 0, -1);
	}

	/**
	 * 获取 List 集合
	 *
	 * @param key   键
	 * @param start 开始索引
	 * @param end   结束索引; -1表示到最后即全部
	 * @param <T>   泛型
	 * @return List 集合
	 */
	public <T> List<T> lGet(final String key, final long start, final long end) {
		return redisTemplate.opsForList().range(key, start, end);
	}

	/**
	 * 获取 List 左边的一个元素
	 *
	 * @param key 键
	 * @param <T> 泛型
	 * @return Set 集合
	 */
	public <T> T lGetLeft(final String key) {
		ListOperations<String, T> ops = redisTemplate.opsForList();
		return ops.leftPop(key);
	}

	/**
	 * 获取 List 右边的一个元素
	 *
	 * @param key 键
	 * @param <T> 泛型
	 * @return Set 集合
	 */
	public <T> T lGetRight(final String key) {
		ListOperations<String, T> ops = redisTemplate.opsForList();
		return ops.rightPop(key);
	}

	// endregion
}
