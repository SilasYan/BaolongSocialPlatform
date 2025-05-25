package icu.baolong.social.common.entity.enums;

import icu.baolong.social.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 判断枚举
 *
 * @author Silas Yan 2025-04-25 21:05
 */
@Getter
@AllArgsConstructor
public enum BoolEnum {

	NO(0, "否"),

	YES(1, "是");

	private final Integer key;

	private final String label;

	private static final Map<Integer, BoolEnum> CACHE;

	static {
		CACHE = Arrays.stream(BoolEnum.values()).collect(Collectors.toMap(BoolEnum::getKey, Function.identity()));
	}

	/**
	 * 根据键获取枚举
	 *
	 * @param key 键
	 * @return 枚举
	 */
	public static BoolEnum of(Integer key) {
		return CACHE.get(key);
	}

	/**
	 * 判断
	 *
	 * @param bool 布尔值
	 * @return 对应值
	 */
	public static Integer is(Boolean bool) {
		if (bool == null) {
			throw new BusinessException("key不能为空");
		}
		return bool ? YES.getKey() : NO.getKey();
	}
}
