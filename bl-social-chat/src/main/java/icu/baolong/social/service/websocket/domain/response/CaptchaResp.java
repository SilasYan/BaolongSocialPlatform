package icu.baolong.social.service.websocket.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 验证码响应类
 *
 * @author Silas Yan 2025-05-20 22:18
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaResp implements Serializable {

	@Schema(description = "验证码KEY")
	private String captchaKey;

	@Schema(description = "验证码内容")
	private String captchaValue;

	@Serial
	private static final long serialVersionUID = 1L;
}
