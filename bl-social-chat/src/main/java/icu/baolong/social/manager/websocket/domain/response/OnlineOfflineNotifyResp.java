package icu.baolong.social.manager.websocket.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 上线下线通知响应类
 *
 * @author Silas Yan 2025-05-20 22:39
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnlineOfflineNotifyResp implements Serializable {

	@Schema(description = "在线人数")
	private Integer onlineNumber;

	@Schema(description = "用户ID")
	private Long userId;

	@Schema(description = "角色ID")
	private Long roleId;

	@Schema(description = "在线状态（0-离线、1-在线）")
	private Integer activeStatus;

	@Schema(description = "最后上线时间")
	private Date lastOnlineTime;

	@Serial
	private static final long serialVersionUID = 1L;
}
