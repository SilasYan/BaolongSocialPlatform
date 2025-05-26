package icu.baolong.social.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务类型枚举
 *
 * @author Silas Yan 2025-05-26 21:07
 */
@Getter
@AllArgsConstructor
public enum BusinessTypeEnum {

	REGISTER("REGISTER", "注册"),
	;

	private final String key;

	private final String label;

}
