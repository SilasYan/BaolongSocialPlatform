package icu.baolong.social.manager.websocket.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录二维码响应类
 *
 * @author Silas Yan 2025-05-20 22:18
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginQRCodeResp implements Serializable {

	@Schema(description = "登录二维码")
	private String qrCodeUrl;

	@Serial
	private static final long serialVersionUID = 1L;
}
