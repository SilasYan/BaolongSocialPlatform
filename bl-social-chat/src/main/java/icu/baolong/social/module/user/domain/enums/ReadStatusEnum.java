package icu.baolong.social.module.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审批阅读状态枚举
 *
 * @author Silas Yan 2025-05-31 00:37
 */
@Getter
@AllArgsConstructor
public enum ReadStatusEnum {

	/**
	 * 未读
	 */
	UNREAD(0, "未读"),
	/**
	 * 已读
	 */
	READ(1, "已读"),
	;

	private final Integer key;

	private final String label;
}
