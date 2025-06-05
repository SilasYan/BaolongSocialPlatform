package icu.baolong.social.module.message.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import icu.baolong.social.module.message.enums.MessageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息响应
 *
 * @author Silas Yan 2025-05-20 22:25
 */
@Schema(name = "消息响应", description = "消息响应")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResp implements Serializable {

	@Schema(description = "用户ID")
	private Long userId;

	@Schema(description = "消息信息")
	private MessageInfo messageInfo;

	@Schema(description = "发送者信息")
	private SenderInfo senderInfo;

	@Schema(description = "交互信息")
	private InteractionInfo interactionInfo;

	/**
	 * 消息信息
	 */
	@Schema(name = "消息信息", description = "消息信息")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MessageInfo {

		@Schema(description = "消息ID")
		private Long messageId;

		@Schema(description = "房间ID")
		private Long roomId;

		@Schema(description = "消息发送时间")
		private Date sendTime;

		/**
		 * @see MessageTypeEnum
		 */
		@Schema(description = "消息类型（1-正常文本、2-撤回消息、3-表情、4-图片、5-视频、6-语音、7-文件、8-系统）")
		private Integer messageType;

		@Schema(description = "消息内容")
		private Object body;
	}

	/**
	 * 发送者信息
	 */
	@Schema(name = "发送者信息", description = "发送者信息")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SenderInfo {

		@Schema(description = "发送者ID")
		private Long senderId;
	}

	/**
	 * 交互信息
	 */
	@Schema(name = "交互信息", description = "交互信息")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class InteractionInfo {

		@Schema(description = "点赞数量")
		private Integer likeQuantity;

		@Schema(description = "点踩数量")
		private Integer dislikeQuantity;

		@Schema(description = "当前用户点赞状态（0-未点赞、1-已点赞）")
		private Boolean userLikeFlag;

		@Schema(description = "当前用户点踩状态（0-未点踩、1-已点踩）")
		private Boolean userDislikeFlag;
	}

	@Serial
	private static final long serialVersionUID = 1L;
}
