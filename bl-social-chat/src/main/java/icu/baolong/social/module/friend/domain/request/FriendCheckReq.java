package icu.baolong.social.module.friend.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 好友检查请求
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
@Schema(name = "好友检查请求", description = "好友检查请求")
@Data
public class FriendCheckReq implements Serializable {

	@Schema(description = "好友ID列表")
	private List<Long> userIdList;

	@Serial
	private static final long serialVersionUID = 1L;
}
