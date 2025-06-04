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
 * 系统配置表
 *
 * @author Baolong
 * @TableName sys_config
 */
@Accessors(chain = true)
@TableName(value = "sys_config")
@Data
public class SysConfig implements Serializable {

	/**
	 * 配置ID
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long configId;

	/**
	 * 配置名称
	 */
	private String configName;

	/**
	 * 配置描述
	 */
	private String configDesc;

	/**
	 * 配置类型（0-值、1-JSON对象、2-JSON数组）
	 */
	private Integer configType;

	/**
	 * 配置键
	 */
	private String configKey;

	/**
	 * 配置值
	 */
	private String configValue;

	/**
	 * 状态（0-开启, 1-关闭）
	 */
	private Integer status;

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
