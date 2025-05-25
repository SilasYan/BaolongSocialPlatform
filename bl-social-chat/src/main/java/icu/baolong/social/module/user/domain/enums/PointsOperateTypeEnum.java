package icu.baolong.social.module.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 积分操作类型枚举
 *
 * @author Silas Yan 2025-05-25 15:30
 */
@Getter
@AllArgsConstructor
public enum PointsOperateTypeEnum {

	/**
	 * 新增
	 */
	INCREASE(0, "新增"),

	/**
	 * 减少
	 */
	REDUCE(1, "减少");

	private final Integer key;

	private final String label;
}
