package icu.baolong.social.function.cache;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import icu.baolong.social.common.utils.RedisUtil;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Redis 缓存抽象类
 * <p>
 * PS: 只限于当前项目, 其他项目请更换 RedisUtil 的实现
 *
 * @author Silas Yan 2025-06-01 19:55
 */
@SuppressWarnings(value = {"unchecked"})
public abstract class AbstractRedisCache<IN, OUT> implements BaseCache<IN, OUT> {

	/**
	 * 泛型出参
	 */
	private final Class<OUT> oc;

	protected AbstractRedisCache() {
		// 获取泛型参数
		this.oc = (Class<OUT>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	/**
	 * 获取 RedisUtil
	 *
	 * @return RedisUtil
	 */
	protected abstract RedisUtil redisUtil();

	/**
	 * 获取缓存 KEY
	 *
	 * @param in 缓存 KEY
	 * @return 缓存 KEY
	 */
	protected abstract String key(IN in);

	/**
	 * 设置缓存过期时间
	 *
	 * @return 过期时间, 默认 1000 * 60 * 5 毫秒
	 */
	protected Long expire() {return 1000 * 60 * 5L;}

	/**
	 * 获取缓存过期时间单位
	 *
	 * @return 单位, 默认毫秒
	 */
	private TimeUnit unit() {return TimeUnit.MILLISECONDS;}

	/**
	 * 加载
	 *
	 * @param ins 缓存 KEY 列表
	 * @return 缓存 MAP
	 */
	protected abstract Map<IN, OUT> load(List<IN> ins);

	/**
	 * 批量获取缓存
	 *
	 * @param ins 缓存 KEY 列表
	 * @return 缓存 MAP
	 */
	@Override
	public Map<IN, OUT> getBatch(List<IN> ins) {
		if (CollUtil.isEmpty(ins)) {
			return Map.of();
		}

		// 去重
		ins = ins.stream().distinct().toList();
		// 获取缓存 KEY
		List<String> keys = ins.stream().map(this::key).toList();
		// 获取已经有的缓存
		List<OUT> existRedisList = Optional.ofNullable(redisUtil().multiGet(keys, oc)).orElse(List.of());
		// 获取没有的缓存
		List<IN> notExistRedisKeys = new ArrayList<>();
		for (int i = 0; i < existRedisList.size(); i++) {
			if (ObjectUtil.isNull(existRedisList.get(i))) {
				notExistRedisKeys.add(ins.get(i));
			}
		}
		Map<IN, OUT> needQueryData = new HashMap<>();
		// 重新获取没有缓存的数据
		if (CollUtil.isNotEmpty(notExistRedisKeys)) {
			needQueryData = load(notExistRedisKeys);
			// 批量设置缓存
			for (Map.Entry<IN, OUT> entry : needQueryData.entrySet()) {
				redisUtil().set(this.key(entry.getKey()), entry.getValue(), expire(), unit());
			}
		}
		// 组装并返回
		Map<IN, OUT> result = new HashMap<>();
		for (int i = 0; i < ins.size(); i++) {
			IN in = ins.get(i);
			OUT out = Optional.ofNullable(existRedisList.get(i)).orElse(needQueryData.get(in));
			result.put(in, out);
		}
		return result;
	}

	/**
	 * 批量删除缓存
	 *
	 * @param ins 缓存 KEY 列表
	 */
	@Override
	public void deleteBatch(List<IN> ins) {
		redisUtil().delete(ins.stream().map(this::key).toList());
	}
}
