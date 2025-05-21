package icu.baolong.social.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户登录日志表
 *
 * @author Baolong
 * @TableName user_login_log
 */
@Accessors(chain = true)
@Data
@TableName(value = "user_login_log")
public class UserLoginLog implements Serializable {
	@Serial
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 登录IP
	 */
	private String loginIp;

	/**
	 * 登录时间
	 */
	private Date loginTime;

	/**
	 * 登录设备信息
	 */
	private String userAgent;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;
}
