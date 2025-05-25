package icu.baolong.social.manager.websocket.domain;

import lombok.Data;

/**
 * 连接实体
 *
 * @author Silas Yan 2025-05-24 22:37
 */
@Data
public class ConnectEntity {

	/**
	 * 用户ID, 当且仅当用户登录时有值
	 */
	private Long userId;
}
