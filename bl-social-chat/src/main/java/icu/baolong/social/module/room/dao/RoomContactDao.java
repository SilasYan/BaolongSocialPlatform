package icu.baolong.social.module.room.dao;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.room.entity.RoomContact;
import icu.baolong.social.repository.room.mapper.RoomContactMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 房间会话表 (room_contact) - 持久化服务
 *
 * @author Baolong 2025-05-30 23:54:59
 */
@Repository
public class RoomContactDao extends ServiceImpl<RoomContactMapper, RoomContact> {

	/**
	 * 更新房间会话表最后一条消息信息
	 *
	 * @param roomId     房间ID
	 * @param userIdList 需要更新的用户ID列表
	 * @param messageId  消息ID
	 * @param sendTime   发送时间
	 */
	public void updateRoomContactLastMessageInfo(Long roomId, List<Long> userIdList, Long messageId, Date sendTime) {
		for (Long userId : userIdList) {
			RoomContact roomContact = new RoomContact();
			roomContact.setUserId(userId)
					.setRoomId(roomId)
					.setLastMsgId(messageId)
					.setLastMsgTime(sendTime);
			this.saveOrUpdate(roomContact);
		}
	}
}
