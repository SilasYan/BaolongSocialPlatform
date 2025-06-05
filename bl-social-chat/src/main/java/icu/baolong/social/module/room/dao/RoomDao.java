package icu.baolong.social.module.room.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.room.entity.Room;
import icu.baolong.social.repository.room.mapper.RoomMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 房间表 (room) - 持久化服务
 *
 * @author Baolong 2025-05-30 23:54:59
 */
@Repository
public class RoomDao extends ServiceImpl<RoomMapper, Room> {

	/**
	 * 更新房间最后一条消息信息
	 *
	 * @param roomId    房间ID
	 * @param messageId 消息ID
	 * @param sendTime  发送时间
	 */
	public void updateRoomLastMessageInfo(Long roomId, Long messageId, Date sendTime) {
		this.lambdaUpdate()
				.set(Room::getLastMsgId, messageId)
				.set(Room::getLastMsgTime, sendTime)
				.eq(Room::getRoomId, roomId)
				.update();
	}
}
