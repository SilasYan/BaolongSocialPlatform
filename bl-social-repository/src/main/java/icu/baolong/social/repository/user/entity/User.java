package icu.baolong.social.repository.user.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @author Baolong
 * @TableName user
 */
@Accessors(chain = true)
@TableName(value = "user", autoResultMap = true)
@Data
public class User implements Serializable {

	/**
	 * 用户ID
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 账号
	 */
	private String userAccount;

	/**
	 * 密码
	 */
	private String userPassword;

	/**
	 * 用户邮箱
	 */
	private String userEmail;

	/**
	 * 用户手机号
	 */
	private String userPhone;

	/**
	 * 用户名称
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
	 * 用户性别（0-男, 1-女, 2-无）
	 */
	private Integer userSex;

	/**
	 * 用户积分
	 */
	private Long userPoints;

	/**
	 * 微信OpenId
	 */
	private String wxOpenId;

	/**
	 * 分享码
	 */
	private String shareCode;

	/**
	 * 徽章ID
	 */
	private Long badgeId;

	/**
	 * 头像框ID
	 */
	private Long avatarFrameId;

	/**
	 * 在线状态（0-下线, 1-上线）
	 */
	private Integer onlineStatus;

	/**
	 * IP信息
	 */
	@TableField(value = "ip_info", typeHandler = JacksonTypeHandler.class)
	private IpInfo ipInfo;

	/**
	 * 首次登录时间
	 */
	private Date firstLoginTime;

	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 连续签到天数
	 */
	private Integer checkInDays;

	/**
	 * 最后签到时间
	 */
	private Date lastCheckInTime;

	/**
	 * 是否禁用（0-正常, 1-禁用）
	 */
	private Integer isDisabled;

	/**
	 * 是否删除（0-正常, 1-删除）
	 */
	private Integer isDelete;

	/**
	 * 编辑时间
	 */
	@TableField(value = "edit_time", fill = FieldFill.UPDATE)
	private Date editTime;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	@Serial
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}
