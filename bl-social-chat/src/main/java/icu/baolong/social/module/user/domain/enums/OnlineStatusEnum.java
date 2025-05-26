package icu.baolong.social.module.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 在线状态枚举
 *
 * @author Silas Yan 2025-05-25 15:30
 */
@Getter
@AllArgsConstructor
public enum OnlineStatusEnum {

	/**
	 * 上线
	 */
	ONLINE(0, "上线"),

	/**
	 * 下线
	 */
	OFFLINE(1, "下线");

	private final Integer key;

	private final String label;
}
