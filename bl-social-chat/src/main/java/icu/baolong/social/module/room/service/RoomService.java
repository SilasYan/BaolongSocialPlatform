package icu.baolong.social.module.room.service;

import java.util.List;

/**
 * 房间表 (room) - 服务接口
 *
 * @author Baolong 2025-05-30 23:54:59
 */
public interface RoomService {

	/**
	 * 创建单聊房间
	 *
	 * @param userIdList 用户列表, 必须有两个用户
	 * @return 房间ID
	 */
	Long createSingleChatRoom(List<Long> userIdList);

	/**
	 * 禁用单聊房间
	 *
	 * @param userIdList 用户列表, 必须有两个用户
	 */
	void disableFriendRoom(List<Long> userIdList);
}
