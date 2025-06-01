package icu.baolong.social.module.friend.domain.request;

import icu.baolong.social.common.base.page.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 好友申请查询请求
 *
 * @author Silas Yan 2025-05-31 15:18
 */
@Schema(name = "好友申请查询请求", description = "好友申请查询请求")
@EqualsAndHashCode(callSuper = true)
@Data
public class FriendApplyQueryReq extends PageRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
}
