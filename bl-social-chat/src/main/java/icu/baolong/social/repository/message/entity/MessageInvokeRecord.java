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
 * 消息调用记录（存放所有未执行的消息）
 *
 * @author Baolong
 * @TableName message_invoke_record
 */
@Accessors(chain = true)
@TableName(value = "message_invoke_record", autoResultMap = true)
@Data
public class MessageInvokeRecord implements Serializable {

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 快照参数（JSON格式）
	 */
	@TableField(value = "snapshot_param", typeHandler = JacksonTypeHandler.class)
	private SnapshotParam snapshotParam;

	/**
	 * 执行状态（1-待执行、2-已失败）
	 */
	private Integer invokeStatus;

	/**
	 * 最大重试次数
	 */
	private Integer maxRetryCount;

	/**
	 * 已经重试次数
	 */
	private Integer retryCount;

	/**
	 * 下一次重试时间
	 */
	private Date nextRetryTime;

	/**
	 * 执行失败的堆栈信息
	 */
	private String failReason;

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
