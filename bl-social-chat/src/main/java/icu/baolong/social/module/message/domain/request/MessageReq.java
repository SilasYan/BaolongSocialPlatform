package icu.baolong.social.module.message.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 消息请求
 *
 * @author Silas Yan 2025-06-03 20:33
 */
@Data
public class MessageReq implements Serializable {

	@Schema(description = "房间ID", requiredMode = Schema.RequiredMode.REQUIRED)
	private Long roomId;

	@Schema(description = "消息类型（0-正常文本、1-撤回消息）", requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer msgType;

	@Schema(description = "消息体", requiredMode = Schema.RequiredMode.REQUIRED)
	private Object body;

	@Serial
	private static final long serialVersionUID = 1L;
}
