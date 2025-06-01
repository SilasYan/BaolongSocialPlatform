package icu.baolong.social.module.room.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.module.room.domain.enums.RoomStatusEnum;
import icu.baolong.social.repository.room.entity.Room;
import icu.baolong.social.repository.room.entity.RoomFriend;
import icu.baolong.social.repository.room.mapper.RoomFriendMapper;
import org.springframework.stereotype.Repository;

/**
 * 单聊房间表 (room_friend) - 持久化服务
 *
 * @author Baolong 2025-05-30 23:54:59
 */
@Repository
public class RoomFriendDao extends ServiceImpl<RoomFriendMapper, RoomFriend> {

	/**
	 * 根据房间key获取单聊房间
	 *
	 * @param roomKey 房间KEY
	 * @return 单聊房间
	 */
	public RoomFriend getRoomFriendByRoomKey(String roomKey) {
		return this.lambdaQuery()
				.eq(RoomFriend::getRoomKey, roomKey)
				.one();
	}

	/**
	 * 恢复单聊房间
	 *
	 * @param roomFriendId 单聊房间ID
	 */
	public boolean restoreRoomFriend(Long roomFriendId) {
		return this.lambdaUpdate()
				.set(RoomFriend::getRoomStatus, RoomStatusEnum.NORMAL.getKey())
				.eq(RoomFriend::getId, roomFriendId)
				.update();
	}

	/**
	 * 禁用单聊房间
	 *
	 * @param roomKey 房间KEY
	 */
	public boolean disableFriendRoom(String roomKey) {
		return this.lambdaUpdate()
				.set(RoomFriend::getRoomStatus, RoomStatusEnum.DISABLED.getKey())
				.eq(RoomFriend::getRoomKey, roomKey)
				.update();
	}
}
