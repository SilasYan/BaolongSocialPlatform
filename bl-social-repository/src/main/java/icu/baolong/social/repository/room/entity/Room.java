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
 * 房间表
 *
 * @author Baolong
 * @TableName room
 */
@Accessors(chain = true)
@TableName(value = "room")
@Data
public class Room implements Serializable {

	/**
	 * 房间ID
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long roomId;

	/**
	 * 房间类型（0-单聊、1-群聊）
	 */
	private Integer roomType;

	/**
	 * 展示类型（0-非全员展示、1-全员展示）
	 */
	private Integer showType;

	/**
	 * 房间中最后一条消息ID
	 */
	private Long lastMsgId;

	/**
	 * 房间中最后一条消息时间（全员群用到）
	 */
	private Date lastMsgTime;

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
