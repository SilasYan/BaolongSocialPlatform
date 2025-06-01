package icu.baolong.social.module.room.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房间类型枚举
 *
 * @author Silas Yan 2025-05-31 00:37
 */
@Getter
@AllArgsConstructor
public enum RoomTypeEnum {

	/**
	 * 单聊
	 */
	SINGLE_CHAT(0, "单聊"),
	/**
	 * 群聊
	 */
	GROUP_CHAT(1, "群聊"),
	;

	private final Integer key;

	private final String label;
}
