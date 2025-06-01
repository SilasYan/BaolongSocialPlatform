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
 * 房间会话表
 *
 * @author Baolong
 * @TableName room_contact
 */
@Accessors(chain = true)
@TableName(value = "room_contact")
@Data
public class RoomContact implements Serializable {

	/**
	 * 会话ID
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 房间ID
	 */
	private Long roomId;

	/**
	 * 会话读取的时间
	 */
	private Date readTime;

	/**
	 * 会话中最后一条消息ID
	 */
	private Long lastMsgId;

	/**
	 * 会话中最后一条消息时间（非全员群用到）
	 */
	private Date lastMsgTime;

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
