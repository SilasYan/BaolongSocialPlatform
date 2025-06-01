package icu.baolong.social.repository.room.entity;

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
 * 群成员表
 *
 * @author Baolong
 * @TableName group_member
 */
@Accessors(chain = true)
@TableName(value = "group_member")
@Data
public class GroupMember implements Serializable {

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 群聊ID
	 */
	private Long groupId;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 成员角色（0-群主、1-管理、2-成员）
	 */
	private Integer memberRole;

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
