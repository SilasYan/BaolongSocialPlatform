package icu.baolong.social.repository.blacklist.entity;

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
 * 黑名单表
 *
 * @author Baolong
 * @TableName blacklist
 */
@Accessors(chain = true)
@TableName(value = "blacklist")
@Data
public class Blacklist implements Serializable {

	/**
	 * 黑名单ID
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 拉黑类型（0-用户ID、1-IP地址）
	 */
	private Integer blackType;

	/**
	 * 拉黑目标
	 */
	private String blackTarget;

	/**
	 * 操作人
	 */
	private Long operator;

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
