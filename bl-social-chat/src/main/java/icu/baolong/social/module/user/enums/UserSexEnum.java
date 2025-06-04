package icu.baolong.social.module.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户性别枚举
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
@Getter
@AllArgsConstructor
public enum UserSexEnum {

	/**
	 * 男
	 */
	BOY(0, "男"),
	/**
	 * 女
	 */
	GIRL(1, "女"),
	/**
	 * 其他
	 */
	OTHER(2, "其他");

	private final Integer key;

	private final String label;

	private static final Map<Integer, UserSexEnum> CACHE;

	static {
		CACHE = Arrays.stream(UserSexEnum.values()).collect(Collectors.toMap(UserSexEnum::getKey, Function.identity()));
	}

	/**
	 * 根据键获取枚举
	 *
	 * @param key 键
	 * @return 枚举
	 */
	public static UserSexEnum of(Integer key) {
		return CACHE.get(key);
	}
}
