package icu.baolong.social.module.room.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房间展示类型枚举
 *
 * @author Silas Yan 2025-05-31 00:37
 */
@Getter
@AllArgsConstructor
public enum ShowTypeEnum {

	/**
	 * 非全员展示
	 */
	UN_ALL_STAFF(0, "非全员展示"),
	/**
	 * 全员展示
	 */
	ALL_STAFF(1, "全员展示"),
	;

	private final Integer key;

	private final String label;
}
