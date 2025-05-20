package icu.baolong.social.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 验证码响应类
 *
 * @author Silas Yan 2025-05-20 22:18
 */
@Data
public class CaptchaResp implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "验证码KEY")
	private String captchaKey;
	@Schema(description = "验证码内容")
	private String captchaValue;
}
