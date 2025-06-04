package icu.baolong.social.module.room.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房间状态枚举
 *
 * @author Silas Yan 2025-05-31 00:37
 */
@Getter
@AllArgsConstructor
public enum RoomStatusEnum {

	/**
	 * 正常
	 */
	NORMAL(0, "正常"),
	/**
	 * 禁用
	 */
	DISABLED(1, "禁用");
	;

	private final Integer key;

	private final String label;
}
