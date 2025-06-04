package icu.baolong.social.repository.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 消息表
 *
 * @author Baolong
 * @TableName message
 */
@Accessors(chain = true)
@TableName(value = "message", autoResultMap = true)
@Data
public class Message implements Serializable {

	/**
	 * 消息ID
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long messageId;

	/**
	 * 房间ID
	 */
	private Long roomId;

	/**
	 * 发送者ID
	 */
	private Long senderId;

	/**
	 * 消息类型（0-正常文本、1-撤回消息）
	 */
	private Integer msgType;

	/**
	 * 消息内容
	 */
	private String content;

	/**
	 * 回复的消息ID
	 */
	private Long replyId;

	/**
	 * 与回复的消息间隔多少条
	 */
	private Integer replyGap;

	/**
	 * 消息状态（0-正常、1-删除）
	 */
	private Integer msgStatus;

	/**
	 * 扩展信息
	 */
	@TableField(value = "extend_info", typeHandler = JacksonTypeHandler.class)
	private ExtendInfo extendInfo;

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
