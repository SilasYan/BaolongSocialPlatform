package icu.baolong.social.module.user.domain.request;

import icu.baolong.social.base.page.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户查询请求
 *
 * @author Silas Yan 2025-04-25 21:02:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryReq extends PageRequest implements Serializable {

	@Schema(description = "用户ID")
	private Long userId;

	@Schema(description = "账号")
	private String userAccount;

	@Schema(description = "用户邮箱")
	private String userEmail;

	@Schema(description = "用户手机号")
	private String userPhone;

	@Schema(description = "用户昵称")
	private String userName;

	@Schema(description = "用户简介")
	private String userProfile;

	@Schema(description = "微信OpenId")
	private String wxOpenId;

	@Schema(description = "分享码")
	private String shareCode;

	@Schema(description = "是否禁用（0-正常, 1-禁用）")
	private Integer isDisabled;

	@Schema(description = "创建时间[开始时间]")
	private String startCreateTime;

	@Schema(description = "创建时间[结束时间]")
	private String endCreateTime;

	@Schema(description = "编辑时间[开始时间]")
	private String startEditTime;

	@Schema(description = "编辑时间[结束时间]")
	private String endEditTime;

	@Serial
	private static final long serialVersionUID = 1L;
}
