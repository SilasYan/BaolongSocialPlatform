package icu.baolong.social.websocket.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 二维码响应类
 *
 * @author Silas Yan 2025-05-20 22:18
 */
@Builder
@Data
public class QRCodeResp implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "二维码地址")
	private String qrCodeUrl;
}
