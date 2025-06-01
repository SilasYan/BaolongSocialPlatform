package icu.baolong.social.module.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申请类型枚举
 *
 * @author Silas Yan 2025-05-31 00:37
 */
@Getter
@AllArgsConstructor
public enum ApplyTypeEnum {

	/**
	 * 加好友
	 */
	ADD_FRIEND(1, "加好友"),
	/**
	 * 加群聊
	 */
	ADD_GROUP(2, "加群聊"),
	;

	private final Integer key;

	private final String label;
}
