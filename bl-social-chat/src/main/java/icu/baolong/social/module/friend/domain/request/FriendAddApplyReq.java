package icu.baolong.social.module.friend.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 好友申请请求
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
@Schema(name = "好友添加申请请求", description = "好友添加申请请求")
@Data
public class FriendAddApplyReq implements Serializable {

	@Schema(description = "目标用户")
	private Long targetUser;

	@Schema(description = "申请内容")
	private String applyContent;

	@Serial
	private static final long serialVersionUID = 1L;
}
