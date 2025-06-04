package icu.baolong.social.module.sys.domain.enums;

import icu.baolong.social.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 允许重复状态枚举
 *
 * @author Silas Yan 2025-05-26 20:45
 */
@Getter
@AllArgsConstructor
public enum RepeatStatusEnum {

	/**
	 * 允许
	 */
	REPEAT(0, "允许"),
	/**
	 * 不允许
	 */
	NOT_REPEAT(1, "不允许");

	private final Integer key;

	private final String label;

	private static final Map<Integer, RepeatStatusEnum> CACHE;

	static {
		CACHE = Arrays.stream(RepeatStatusEnum.values()).collect(Collectors.toMap(RepeatStatusEnum::getKey, Function.identity()));
	}

	/**
	 * 根据键获取枚举
	 *
	 * @param key 键
	 * @return 枚举
	 */
	public static RepeatStatusEnum of(Integer key) {
		return CACHE.get(key);
	}

	/**
	 * 是否允许重复
	 *
	 * @param key 键
	 * @return 是否允许重复
	 */
	public static boolean isRepeat(Integer key) {
		if (key == null) {
			throw new BusinessException("key不能为空");
		}
		return REPEAT.getKey().equals(key);
	}
}
