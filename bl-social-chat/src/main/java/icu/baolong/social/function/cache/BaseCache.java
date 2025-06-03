package icu.baolong.social.function.cache;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 基础缓存接口
 *
 * @author Silas Yan 2025-06-01 19:48
 */
public interface BaseCache<IN, OUT> {

	/**
	 * 获取缓存
	 *
	 * @param in 缓存 KEY
	 * @return 缓存值
	 */
	default OUT get(IN in) {
		return getBatch(Collections.singletonList(in)).get(in);
	}

	/**
	 * 批量获取缓存
	 *
	 * @param ins 缓存 KEY 列表
	 * @return 缓存 MAP
	 */
	Map<IN, OUT> getBatch(List<IN> ins);

	/**
	 * 删除缓存
	 *
	 * @param in 缓存 KEY
	 */
	default void delete(IN in) {
		deleteBatch(Collections.singletonList(in));
	}

	/**
	 * 批量删除缓存
	 *
	 * @param ins 缓存 KEY 列表
	 */
	void deleteBatch(List<IN> ins);
}
