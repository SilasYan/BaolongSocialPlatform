package icu.baolong.social.repository.sys.entity;

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
 * 系统权限表
 *
 * @author Baolong
 * @TableName sys_permission
 */
@Accessors(chain = true)
@TableName(value = "sys_permission")
@Data
public class SysPermission implements Serializable {

	/**
	 * 权限ID
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 权限名称
	 */
	private String permName;

	/**
	 * 权限标识
	 */
	private String permSign;

	/**
	 * 权限描述
	 */
	private String permDesc;

	/**
	 * 是否删除（0-正常, 1-删除）
	 */
	private Integer isDelete;

	/**
	 * 编辑时间
	 */
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
