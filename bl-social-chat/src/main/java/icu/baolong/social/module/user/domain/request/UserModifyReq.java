package icu.baolong.social.module.user.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户修改请求（只修改唯一的字段）
 *
 * @author Silas Yan 2025-05-25 17:20
 */
@Data
public class UserModifyReq implements Serializable {

	@Schema(description = "账号")
	private String userAccount;

	@Schema(description = "用户邮箱")
	private String userEmail;

	@Schema(description = "用户手机号")
	private String userPhone;

	@Schema(description = "用户昵称")
	private String userName;

	@Serial
	private static final long serialVersionUID = 1L;
}
