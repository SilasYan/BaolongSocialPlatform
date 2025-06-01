package icu.baolong.social.module.message.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
	TEXT(0, "正常文本"),
	/**
	 * 撤回消息
	 */
	WITHDRAW(1, "撤回消息"),
	;

	private final Integer key;

	private final String label;
}
