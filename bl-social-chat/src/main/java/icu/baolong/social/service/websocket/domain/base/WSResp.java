package icu.baolong.social.service.websocket.domain.base;

import icu.baolong.social.service.websocket.domain.enums.WSRespTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * WebSocket响应类
 *
 * @author Silas Yan 2025-05-20 22:02
 */
@Builder
@Data
public class WSResp<T> implements Serializable {

	/**
	 * @see WSRespTypeEnum
	 */
	@Schema(description = "响应消息类型")
	private Integer type;

	@Schema(description = "响应数据")
	private T data;

	@Serial
	private static final long serialVersionUID = 1L;
}
