package icu.baolong.social.repository.user.entity;

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
 * 用户积分日志表
 *
 * @author Baolong
 * @TableName user_points_log
 */
@Accessors(chain = true)
@TableName(value = "user_points_log")
@Data
public class UserPointsLog implements Serializable {

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
	 * 操作类型（0-新增, 1-减少）
	 */
	private Integer operateType;

	/**
	 * 操作数量
	 */
	private Long operateQuantity;

	/**
	 * 操作描述
	 */
	private String operateDesc;

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
