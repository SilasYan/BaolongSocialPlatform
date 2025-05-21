package icu.baolong.social.user.enums;

import icu.baolong.social.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户禁用状态枚举
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
@Getter
@AllArgsConstructor
public enum UserDisabledEnum {

	/**
	 * 正常
	 */
	NORMAL(0, "正常"),
	/**
	 * 禁用
	 */
	DISABLED(1, "禁用");

	private final Integer key;

	private final String label;

	private static final Map<Integer, UserDisabledEnum> CACHE;

	static {
		CACHE = Arrays.stream(UserDisabledEnum.values()).collect(Collectors.toMap(UserDisabledEnum::getKey, Function.identity()));
	}

	/**
	 * 根据键获取枚举
	 *
	 * @param key 键
	 * @return 枚举
	 */
	public static UserDisabledEnum of(Integer key) {
		return CACHE.get(key);
	}

	/**
	 * 是否禁用
	 *
	 * @param key 键
	 * @return 是否禁用
	 */
	public static boolean isDisabled(Integer key) {
		if (key == null) {
			throw new BusinessException("key不能为空");
		}
		return DISABLED.getKey().equals(key);
	}
}
