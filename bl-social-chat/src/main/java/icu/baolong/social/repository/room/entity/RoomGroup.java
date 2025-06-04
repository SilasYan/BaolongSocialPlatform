package icu.baolong.social.repository.room.entity;

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
 * 群聊房间表
 *
 * @author Baolong
 * @TableName room_group
 */
@Accessors(chain = true)
@TableName(value = "room_group")
@Data
public class RoomGroup implements Serializable {

	/**
	 * 群聊ID
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long groupId;

	/**
	 * 房间ID
	 */
	private Long roomId;

	/**
	 * 群主ID
	 */
	private Long leaderId;

	/**
	 * 群名称
	 */
	private String groupName;

	/**
	 * 群头像
	 */
	private String groupAvatar;

	/**
	 * 扩展信息（根据不同类型房间存储不同的信息）
	 */
	private Object extendInfo;

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
