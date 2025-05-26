package icu.baolong.social.repository.items.entity;

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
 * 物品表
 *
 * @author Baolong
 * @TableName items
 */
@Accessors(chain = true)
@TableName(value = "items")
@Data
public class Items implements Serializable {

	/**
	 * 物品ID
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 物品类型（0-改名卡, 1-徽章, 2-头像框）
	 */
	private Integer itemType;

	/**
	 * 物品名称
	 */
	private String itemName;

	/**
	 * 物品描述
	 */
	private String itemDesc;

	/**
	 * 物品图片
	 */
	private String itemImage;

	/**
	 * 允许重复（0-允许, 1-不允许）
	 */
	private Integer repeatStatus;

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
