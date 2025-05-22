package icu.baolong.social.module.user.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 图形验证码响应
 *
 * @author Silas Yan 2025-05-21 23:17
 */
@Builder
@Data
public class GraphicCodeResp implements Serializable {

	@Schema(description = "图形验证码KEY")
	private String captchaKey;

	@Schema(description = "图形验证码图片, Base64格式")
	private String imageBase64;

	@Serial
	private static final long serialVersionUID = 1L;
}
