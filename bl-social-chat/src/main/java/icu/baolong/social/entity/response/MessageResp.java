package icu.baolong.social.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息响应类
 *
 * @author Silas Yan 2025-05-20 22:25
 */
@Builder
@Data
public class MessageResp implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "用户ID")
	private Long userId;
	@Schema(description = "消息ID")
	private Long messageId;
	@Schema(description = "房间ID")
	private Long roomId;
	@Schema(description = "消息发送时间")
	private Date sendTime;
	@Schema(description = "消息类型（1-正常消息、2-撤回消息）")
	private Integer messageType;
	@Schema(description = "消息内容")
	private Object body;
	@Schema(description = "点赞数量")
	private Integer likeQuantity;
	@Schema(description = "点踩数量")
	private Integer dislikeQuantity;
	@Schema(description = "当前用户点赞状态（0-未点赞、1-已点赞）")
	private Boolean userLikeFlag;
	@Schema(description = "当前用户点踩状态（0-未点踩、1-已点踩）")
	private Boolean userDislikeFlag;
}
