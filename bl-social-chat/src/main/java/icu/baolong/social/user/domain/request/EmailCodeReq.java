package icu.baolong.social.user.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 邮箱验证码请求
 *
 * @author Silas Yan 2025-05-21 22:12
 */
@Data
public class EmailCodeReq implements Serializable {

	@Schema(description = "用户邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
	private String userEmail;

	@Serial
	private static final long serialVersionUID = 1L;
}
