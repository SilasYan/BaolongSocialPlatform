package icu.baolong.social.module.message.domain.request;

import icu.baolong.social.module.message.enums.MessageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 消息请求
 *
 * @author Silas Yan 2025-06-03 20:33
 */
@Schema(name = "消息请求", description = "消息请求")
@Data
public class MessageReq implements Serializable {

	@Schema(description = "房间ID", requiredMode = Schema.RequiredMode.REQUIRED)
	private Long roomId;

	/**
	 * @see MessageTypeEnum
	 */
	@Schema(description = "消息类型（1-正常文本、2-撤回消息、3-表情、4-图片、5-视频、6-语音、7-文件、8-系统）", requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer messageType;

	@Schema(description = "消息体", requiredMode = Schema.RequiredMode.REQUIRED)
	private Object body;

	@Serial
	private static final long serialVersionUID = 1L;
}
