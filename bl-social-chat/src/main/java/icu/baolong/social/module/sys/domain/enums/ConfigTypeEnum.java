package icu.baolong.social.module.sys.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统配置类型枚举
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
@Getter
@AllArgsConstructor
public enum ConfigTypeEnum {

	/**
	 * 值
	 */
	VALUE(0, "值"),
	/**
	 * JSON对象
	 */
	JSON_OBJECT(1, "JSON对象"),
	/**
	 * JSON数组
	 */
	JSON_ARRAY(2, "JSON数组"),
	;

	private final Integer key;

	private final String label;
}
