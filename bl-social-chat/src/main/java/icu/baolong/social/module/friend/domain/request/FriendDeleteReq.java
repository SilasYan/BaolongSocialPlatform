package icu.baolong.social.module.friend.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 好友删除请求
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
@Schema(name = "好友删除请求", description = "好友删除请求")
@Data
public class FriendDeleteReq implements Serializable {

	@Schema(description = "好友ID")
	private Long friendId;

	@Serial
	private static final long serialVersionUID = 1L;
}
