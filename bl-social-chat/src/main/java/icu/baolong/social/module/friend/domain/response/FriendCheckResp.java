package icu.baolong.social.module.friend.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 好友校验响应
 *
 * @author Silas Yan 2025-05-21 22:19
 */
@Schema(name = "好友校验响应", description = "好友校验响应")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendCheckResp implements Serializable {

	@Schema(description = "好友ID")
	private Long userId;

	@Schema(description = "是否是好友")
	private Boolean isFriend;

	@Serial
	private static final long serialVersionUID = 1L;
}
