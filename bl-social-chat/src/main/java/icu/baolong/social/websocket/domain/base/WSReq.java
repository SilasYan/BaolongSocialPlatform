package icu.baolong.social.websocket.domain.base;

import icu.baolong.social.websocket.domain.enums.WSReqTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * WebSocket请求类
 *
 * @author Silas Yan 2025-05-20 22:02
 */
@Data
public class WSReq {

	/**
	 * @see WSReqTypeEnum
	 */
	@Schema(description = "响应消息类型")
	private Integer type;

	@Schema(description = "请求数据")
	private String data;
}
