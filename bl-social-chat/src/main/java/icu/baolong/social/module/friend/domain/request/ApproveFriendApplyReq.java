package icu.baolong.social.module.friend.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 审批好友申请请求
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
@Schema(name = "审批好友申请请求", description = "审批好友申请请求")
@Data
public class ApproveFriendApplyReq implements Serializable {

	@Schema(description = "申请记录ID")
	private Long applyId;

	@Schema(description = "审批状态（1-同意、2-拒绝）")
	private Integer approveStatus;

	@Serial
	private static final long serialVersionUID = 1L;
}
