package icu.baolong.social.module.blacklist.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 拉黑类型枚举
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
@Getter
@AllArgsConstructor
public enum BlackTypeEnum {

	/**
	 * 用户ID
	 */
	USERID(0, "用户ID"),
	/**
	 * IP地址
	 */
	IP(1, "IP地址"),
	;

	private final Integer key;

	private final String label;
}
