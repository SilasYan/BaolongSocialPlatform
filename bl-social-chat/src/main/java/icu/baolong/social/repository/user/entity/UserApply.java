package icu.baolong.social.repository.user.entity;

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
 * 用户申请表
 *
 * @author Baolong
 * @TableName user_apply
 */
@Accessors(chain = true)
@TableName(value = "user_apply")
@Data
public class UserApply implements Serializable {

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long applyId;

	/**
	 * 申请人ID
	 */
	private Long applyUser;

	/**
	 * 申请类型（1-加好友、2-加群聊）
	 */
	private Integer applyType;

	/**
	 * 接收人ID
	 */
	private Long targetUser;

	/**
	 * 申请内容
	 */
	private String applyContent;

	/**
	 * 申请状态（0-待审批、1-同意、2-拒绝）
	 */
	private Integer applyStatus;

	/**
	 * 阅读状态（0-未读、1-已读）
	 */
	private Integer readStatus;

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
