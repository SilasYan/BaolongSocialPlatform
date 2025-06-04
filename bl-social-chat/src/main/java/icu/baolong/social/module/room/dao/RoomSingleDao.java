package icu.baolong.social.module.room.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.module.room.enums.RoomStatusEnum;
import icu.baolong.social.repository.room.entity.RoomSingle;
import icu.baolong.social.repository.room.mapper.RoomSingleMapper;
import org.springframework.stereotype.Repository;

/**
 * 单聊房间表 (room_single) - 持久化服务
 *
 * @author Baolong 2025-05-30 23:54:59
 */
@Repository
public class RoomSingleDao extends ServiceImpl<RoomSingleMapper, RoomSingle> {

	/**
	 * 根据房间key获取单聊房间
	 *
	 * @param roomKey 房间KEY
	 * @return 单聊房间
	 */
	public RoomSingle getRoomFriendByRoomKey(String roomKey) {
		return this.lambdaQuery()
				.eq(RoomSingle::getRoomKey, roomKey)
				.one();
	}

	/**
	 * 恢复单聊房间
	 *
	 * @param roomFriendId 单聊房间ID
	 */
	public boolean restoreRoomFriend(Long roomFriendId) {
		return this.lambdaUpdate()
				.set(RoomSingle::getRoomStatus, RoomStatusEnum.NORMAL.getKey())
				.eq(RoomSingle::getSingleId, roomFriendId)
				.update();
	}

	/**
	 * 禁用单聊房间
	 *
	 * @param roomKey 房间KEY
	 */
	public boolean disableFriendRoom(String roomKey) {
		return this.lambdaUpdate()
				.set(RoomSingle::getRoomStatus, RoomStatusEnum.DISABLED.getKey())
				.eq(RoomSingle::getRoomKey, roomKey)
				.update();
	}

	/**
	 * 根据房间ID获取单聊房间
	 *
	 * @param roomId 房间ID
	 * @return 单聊房间
	 */
	public RoomSingle getRoomFriendByRoomId(Long roomId) {
		return this.lambdaQuery()
				.eq(RoomSingle::getRoomId, roomId)
				.one();
	}
}
