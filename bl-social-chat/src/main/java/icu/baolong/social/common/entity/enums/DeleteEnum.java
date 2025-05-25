package icu.baolong.social.common.entity.enums;

import icu.baolong.social.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 删除枚举
 *
 * @author Silas Yan 2025-04-25 21:05
 */
@Getter
@AllArgsConstructor
public enum DeleteEnum {

	/**
	 * 正常
	 */
	NORMAL(0, "正常"),

	/**
	 * 删除
	 */
	DELETE(1, "删除");

	private final Integer key;

	private final String label;

	private static final Map<Integer, DeleteEnum> CACHE;

	static {
		CACHE = Arrays.stream(DeleteEnum.values()).collect(Collectors.toMap(DeleteEnum::getKey, Function.identity()));
	}

	/**
	 * 根据键获取枚举
	 *
	 * @param key 键
	 * @return 枚举
	 */
	public static DeleteEnum of(Integer key) {
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
	 * 是否删除
	 *
	 * @param key 键
	 * @return 是否删除
	 */
	public static boolean isDelete(Integer key) {
		if (key == null) {
			throw new BusinessException("key不能为空");
		}
		return DELETE.getKey().equals(key);
	}
}
