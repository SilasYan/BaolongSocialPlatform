package icu.baolong.social.module.room.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 成员角色枚举
 *
 * @author Silas Yan 2025-05-31 00:37
 */
@Getter
@AllArgsConstructor
public enum MemberRoleEnum {

	/**
	 * 群主
	 */
	GROUP_LEADER(0, "群主"),
	/**
	 * 管理
	 */
	GROUP_MANAGE(1, "管理"),
	/**
	 * 成员
	 */
	GROUP_MEMBER(2, "成员"),
	;

	private final Integer key;

	private final String label;
}
