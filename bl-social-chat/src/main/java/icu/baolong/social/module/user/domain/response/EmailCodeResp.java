package icu.baolong.social.module.user.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 邮箱验证码响应
 *
 * @author Silas Yan 2025-05-21 22:19
 */
@Builder
@Data
public class EmailCodeResp implements Serializable {

	@Schema(description = "邮箱验证码KEY")
	private String codeKey;

	@Serial
	private static final long serialVersionUID = 1L;
}
