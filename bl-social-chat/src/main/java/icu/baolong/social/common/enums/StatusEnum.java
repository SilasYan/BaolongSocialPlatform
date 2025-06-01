package icu.baolong.social.common.enums;

import icu.baolong.social.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 状态枚举
 *
 * @author Silas Yan 2025-04-25 21:05
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {

	/**
	 * 正常
	 */
	NORMAL(0, "正常"),

	/**
	 * 不正常
	 */
	ABNORMAL(1, "不正常");

	private final Integer key;

	private final String label;

	private static final Map<Integer, StatusEnum> CACHE;

	static {
		CACHE = Arrays.stream(StatusEnum.values()).collect(Collectors.toMap(StatusEnum::getKey, Function.identity()));
	}

	/**
	 * 根据键获取枚举
	 *
	 * @param key 键
	 * @return 枚举
	 */
	public static StatusEnum of(Integer key) {
		return CACHE.get(key);
	}

	/**
	 * 是否正常
	 *
	 * @param key 键
	 * @return 是否正常
	 */
	public static boolean isNormal(Integer key) {
		if (key == null) {
			throw new BusinessException("key不能为空");
		}
		return NORMAL.getKey().equals(key);
	}

	/**
	 * 是否不正常
	 *
	 * @param key 键
	 * @return 是否不正常
	 */
	public static boolean isAbnormal(Integer key) {
		if (key == null) {
			throw new BusinessException("key不能为空");
		}
		return ABNORMAL.getKey().equals(key);
	}
}
