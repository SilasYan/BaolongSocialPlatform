package icu.baolong.social.module.user.enums;

import icu.baolong.social.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 背包物品使用状态枚举
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
@Getter
@AllArgsConstructor
public enum ItemUseStatusEnum {

	/**
	 * 未使用
	 */
	NOT_USE(0, "未使用"),
	/**
	 * 已使用
	 */
	USE(1, "已使用");

	private final Integer key;

	private final String label;

	private static final Map<Integer, ItemUseStatusEnum> CACHE;

	static {
		CACHE = Arrays.stream(ItemUseStatusEnum.values()).collect(Collectors.toMap(ItemUseStatusEnum::getKey, Function.identity()));
	}

	/**
	 * 根据键获取枚举
	 *
	 * @param key 键
	 * @return 枚举
	 */
	public static ItemUseStatusEnum of(Integer key) {
		return CACHE.get(key);
	}

	/**
	 * 是否使用
	 *
	 * @param key 键
	 * @return 是否使用
	 */
	public static boolean isUsed(Integer key) {
		if (key == null) {
			throw new BusinessException("key不能为空");
		}
		return USE.getKey().equals(key);
	}
}
