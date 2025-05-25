package icu.baolong.social.module.items.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 物品类型枚举
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
@Getter
@AllArgsConstructor
public enum ItemTypeEnum {

	/**
	 * 改名卡
	 */
	NAME_CHANGE_CARD(0, "改名卡"),
	/**
	 * 徽章
	 */
	BADGE(1, "徽章"),
	/**
	 * 头像框
	 */
	AVATAR_FRAME(2, "头像框"),
	;

	private final Integer key;

	private final String label;

	private static final Map<Integer, ItemTypeEnum> CACHE;

	static {
		CACHE = Arrays.stream(ItemTypeEnum.values()).collect(Collectors.toMap(ItemTypeEnum::getKey, Function.identity()));
	}

	/**
	 * 根据 key 判断是否属于当前枚举, 返回 boolean
	 *
	 * @param key 键
	 * @return 是否属于
	 */
	public static boolean contains(Integer key) {
		return CACHE.containsKey(key);
	}
}
