package icu.baolong.social.module.friend.domain.response;

import icu.baolong.social.module.user.enums.OnlineStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 好友响应
 *
 * @author Silas Yan 2025-05-21 22:19
 */
@Schema(name = "好友响应", description = "好友响应")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendResp implements Serializable {

	@Schema(description = "好友ID")
	private Long userId;

	@Schema(description = "好友名称")
	private String userName;

	@Schema(description = "好友头像")
	private String userAvatar;

	/**
	 * @see OnlineStatusEnum
	 */
	@Schema(description = "在线状态（0-下线, 1-上线）")
	private Integer onlineStatus;

	@Serial
	private static final long serialVersionUID = 1L;
}
