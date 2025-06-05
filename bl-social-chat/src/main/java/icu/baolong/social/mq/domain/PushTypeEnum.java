package icu.baolong.social.mq.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 推送类型枚举
 *
 * @author Silas Yan 2025-05-31 00:37
 */
@Getter
@AllArgsConstructor
public enum PushTypeEnum {

	/**
	 * 用户
	 */
	USER(0, "用户"),
	/**
	 * 全员
	 */
	ALL(1, "全员"),
	;

	private final Integer key;

	private final String label;

	private static final Map<Integer, PushTypeEnum> CACHE;

	static {
		CACHE = Arrays.stream(PushTypeEnum.values()).collect(Collectors.toMap(PushTypeEnum::getKey, Function.identity()));
	}

	/**
	 * 根据键获取枚举
	 *
	 * @param key 键
	 * @return 枚举
	 */
	public static PushTypeEnum of(Integer key) {
		return CACHE.get(key);
	}
}
