package icu.baolong.social.service.websocket.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录授权响应类
 *
 * @author Silas Yan 2025-05-20 22:18
 */
@Builder
@Data
public class LoginAuthorizeResp implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
}
