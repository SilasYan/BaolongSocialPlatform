package icu.baolong.social.module.blacklist.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 拉黑请求
 *
 * @author Silas Yan 2025-05-29 20:23
 */
@Data
public class BlockReq implements Serializable {

	@Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
	private Long userId;

	@Serial
	private static final long serialVersionUID = 1L;
}
