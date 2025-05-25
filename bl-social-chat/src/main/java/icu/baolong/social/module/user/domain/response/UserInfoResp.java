package icu.baolong.social.module.user.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息响应
 *
 * @author Silas Yan 2025-05-21 22:19
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResp implements Serializable {

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 账号
	 */
	private String userAccount;

	/**
	 * 用户邮箱
	 */
	private String userEmail;

	/**
	 * 用户手机号
	 */
	private String userPhone;

	/**
	 * 用户昵称
	 */
	private String userName;

	/**
	 * 用户头像
	 */
	private String userAvatar;

	/**
	 * 用户简介
	 */
	private String userProfile;

	/**
	 * 微信OpenId
	 */
	private String wxOpenId;

	/**
	 * 分享码
	 */
	private String shareCode;

	/**
	 * 编辑时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date editTime;

	@Serial
	private static final long serialVersionUID = 1L;
}
