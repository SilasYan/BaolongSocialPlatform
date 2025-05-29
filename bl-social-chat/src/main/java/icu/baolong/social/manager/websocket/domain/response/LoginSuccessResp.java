package icu.baolong.social.manager.websocket.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录成功响应类
 *
 * @author Silas Yan 2025-05-20 22:18
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginSuccessResp implements Serializable {

	@Schema(description = "用户Token")
	private String token;

	@Schema(description = "用户ID")
	private Long userId;

	@Schema(description = "用户头像")
	private String userAvatar;

	@Schema(description = "用户名称")
	private String userName;

	@Schema(description = "用户角色")
	private Integer role;

	@Serial
	private static final long serialVersionUID = 1L;
}
