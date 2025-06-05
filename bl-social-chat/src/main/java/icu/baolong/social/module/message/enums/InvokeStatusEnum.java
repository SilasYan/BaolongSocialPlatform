package icu.baolong.social.module.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 执行状态枚举
 *
 * @author Silas Yan 2025-05-31 00:37
 */
@Getter
@AllArgsConstructor
public enum InvokeStatusEnum {

	/**
	 * 等待
	 */
	WITH(0, "等待"),
	/**
	 * 失败
	 */
	FAIL(1, "失败"),
	;

	private final Integer key;

	private final String label;

	private static final Map<Integer, InvokeStatusEnum> CACHE;

	static {
		CACHE = Arrays.stream(InvokeStatusEnum.values()).collect(Collectors.toMap(InvokeStatusEnum::getKey, Function.identity()));
	}

	/**
	 * 根据键获取枚举
	 *
	 * @param key 键
	 * @return 枚举
	 */
	public static InvokeStatusEnum of(Integer key) {
		return CACHE.get(key);
	}
}
