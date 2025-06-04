package icu.baolong.social.module.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申请状态枚举
 *
 * @author Silas Yan 2025-05-31 00:37
 */
@Getter
@AllArgsConstructor
public enum ApplyStatusEnum {

	/**
	 * 待审批
	 */
	UNAPPROVED(0, "待审批"),
	/**
	 * 同意
	 */
	AGREE(1, "同意"),
	/**
	 * 拒绝
	 */
	REFUSE(2, "拒绝"),
	;

	private final Integer key;

	private final String label;
}
