package icu.baolong.social.entity.request;

import icu.baolong.social.enums.WebSocketReqTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * WebSocket 请求类
 *
 * @author Silas Yan 2025-05-20 22:02
 */
@Data
public class WebSocketReq {

	/**
	 * @see WebSocketReqTypeEnum
	 */
	@Schema(description = "响应消息类型")
	private Integer type;
	@Schema(description = "请求数据")
	private String data;
}
