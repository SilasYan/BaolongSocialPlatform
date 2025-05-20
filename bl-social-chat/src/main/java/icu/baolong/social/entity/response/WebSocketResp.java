package icu.baolong.social.entity.response;

import icu.baolong.social.enums.WebSocketRespTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * WebSocket 响应类
 *
 * @author Silas Yan 2025-05-20 22:02
 */
@Data
public class WebSocketResp<T> {

	/**
	 * @see WebSocketRespTypeEnum
	 */
	@Schema(description = "响应消息类型")
	private Integer type;
	@Schema(description = "响应数据")
	private T data;
}
