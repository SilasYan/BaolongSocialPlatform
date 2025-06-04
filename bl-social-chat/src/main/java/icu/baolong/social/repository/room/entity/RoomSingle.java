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
 * 单聊房间表
 *
 * @author Baolong
 * @TableName room_single
 */
@Accessors(chain = true)
@TableName(value = "room_single")
@Data
public class RoomSingle implements Serializable {

	/**
	 * 单聊ID
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long singleId;

	/**
	 * 房间ID
	 */
	private Long roomId;

	/**
	 * 用户ID1（较小的ID）
	 */
	private Long userId1;

	/**
	 * 用户ID2（较大的ID）
	 */
	private Long userId2;

	/**
	 * 房间key（由两个用户ID根据逗号拼接, 需要先做排序再拼接）
	 */
	private String roomKey;

	/**
	 * 房间状态（0-正常、1-禁用, 删好友了）
	 */
	private Integer roomStatus;

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
