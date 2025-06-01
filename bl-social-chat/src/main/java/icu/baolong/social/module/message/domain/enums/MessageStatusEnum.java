package icu.baolong.social.module.message.domain.enums;

import icu.baolong.social.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 消息状态枚举
 *
 * @author Silas Yan 2025-05-31 00:37
 */
@Getter
@AllArgsConstructor
public enum MessageStatusEnum {

	/**
	 * 正常
	 */
	NORMAL(0, "正常"),
	/**
	 * 删除
	 */
	DELETE(1, "删除"),
	;

	private final Integer key;

	private final String label;

	private static final Map<Integer, MessageStatusEnum> CACHE;

	static {
		CACHE = Arrays.stream(MessageStatusEnum.values()).collect(Collectors.toMap(MessageStatusEnum::getKey, Function.identity()));
	}

	/**
	 * 根据键获取枚举
	 *
	 * @param key 键
	 * @return 枚举
	 */
	public static MessageStatusEnum of(Integer key) {
		return CACHE.get(key);
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
