package icu.baolong.social.repository.user.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * 用户背包表
 *
 * @author Baolong
 * @TableName user_backpack
 */
@Accessors(chain = true)
@TableName(value = "user_backpack")
@Data
public class UserBackpack implements Serializable {

	/**
	 * ID
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 物品ID
	 */
	private Long itemId;

	/**
	 * 幂等号
	 */
	private String idempotent;

	/**
	 * 使用状态（0-待使用, 1-已使用）
	 */
	private Integer useStatus;

	/**
	 * 使用时间
	 */
	private Date useTime;

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
