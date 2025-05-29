package icu.baolong.social.manager.websocket.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Token失效响应类
 *
 * @author Silas Yan 2025-05-20 22:18
 */
@Builder
@Data
public class TokenInvalidateResp implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
}
