package icu.baolong.social.websocket.domain.base;

import icu.baolong.social.websocket.domain.enums.WSRespTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * WebSocket响应类
 *
 * @author Silas Yan 2025-05-20 22:02
 */
@Builder
@Data
public class WSResp<T> {

	/**
	 * @see WSRespTypeEnum
	 */
	@Schema(description = "响应消息类型")
	private Integer type;

	@Schema(description = "响应数据")
	private T data;
}
