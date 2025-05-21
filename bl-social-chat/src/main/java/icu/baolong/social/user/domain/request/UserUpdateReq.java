package icu.baolong.social.user.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户更新请求
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
@Data
public class UserUpdateReq implements Serializable {

	@Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
	private Long userId;

	@Schema(description = "账号")
	private String userAccount;

	@Schema(description = "用户手机号")
	private String userPhone;

	@Schema(description = "用户昵称")
	private String userName;

	@Schema(description = "用户头像")
	private String userAvatar;

	@Schema(description = "用户简介")
	private String userProfile;

	@Serial
	private static final long serialVersionUID = 1L;
}
