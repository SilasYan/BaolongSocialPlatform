package icu.baolong.social.repository.sys.entity;

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
 * 系统角色表
 *
 * @author Baolong
 * @TableName sys_role
 */
@Accessors(chain = true)
@TableName(value = "sys_role")
@Data
public class SysRole implements Serializable {

	/**
	 * 角色ID
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long roleId;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色标识
	 */
	private String roleSign;

	/**
	 * 角色描述
	 */
	private String roleDesc;

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
