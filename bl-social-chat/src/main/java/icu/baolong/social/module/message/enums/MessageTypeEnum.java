package icu.baolong.social.module.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 消息类型枚举
 *
 * @author Silas Yan 2025-05-31 00:37
 */
@Getter
@AllArgsConstructor
public enum MessageTypeEnum {

	/**
	 * 正常文本
	 */
	TEXT(1, "正常文本"),
	/**
	 * 撤回消息
	 */
	RECALL(2, "撤回消息"),
	/**
	 * 表情
	 */
	EMOJI(3, "表情"),
	/**
	 * 图片
	 */
	IMAGE(4, "图片"),
	/**
	 * 视频
	 */
	VIDEO(5, "视频"),
	/**
	 * 语音
	 */
	AUDIO(6, "语音"),
	/**
	 * 文件
	 */
	FILE(7, "文件"),
	/**
	 * 系统
	 */
	SYSTEM(9, "系统"),
	;

	private final Integer key;

	private final String label;

	private static final Map<Integer, MessageTypeEnum> CACHE;

	static {
		CACHE = Arrays.stream(MessageTypeEnum.values()).collect(Collectors.toMap(MessageTypeEnum::getKey, Function.identity()));
	}

	/**
	 * 根据键获取枚举
	 *
	 * @param key 键
	 * @return 枚举
	 */
	public static MessageTypeEnum of(Integer key) {
		return CACHE.get(key);
	}
}
