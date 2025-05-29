package icu.baolong.social.manager.websocket.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 拉黑用户响应类
 *
 * @author Silas Yan 2025-05-29 22:55
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlackUserResp implements Serializable {

	@Schema(description = "用户ID")
	private Long userId;

	@Serial
	private static final long serialVersionUID = 1L;
}
