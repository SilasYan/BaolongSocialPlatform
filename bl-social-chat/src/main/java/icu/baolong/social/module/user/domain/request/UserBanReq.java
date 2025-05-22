package icu.baolong.social.module.user.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户禁用请求
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
@Data
public class UserBanReq implements Serializable {

	@Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
	private Long userId;

	@Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer status;

	@Serial
	private static final long serialVersionUID = 1L;
}
