package icu.baolong.social.module.room.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import icu.baolong.social.common.constants.BaseConstant;
import icu.baolong.social.common.exception.ThrowUtil;
import icu.baolong.social.module.room.dao.RoomDao;
import icu.baolong.social.module.room.dao.RoomFriendDao;
import icu.baolong.social.module.room.domain.enums.RoomStatusEnum;
import icu.baolong.social.module.room.domain.enums.RoomTypeEnum;
import icu.baolong.social.module.room.domain.enums.ShowTypeEnum;
import icu.baolong.social.module.room.service.RoomService;
import icu.baolong.social.repository.room.entity.Room;
import icu.baolong.social.repository.room.entity.RoomFriend;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 房间表 (room) - 服务是西安
 *
 * @author Baolong 2025-05-30 23:54:59
 */
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

	private final RoomDao roomDao;
	private final RoomFriendDao roomFriendDao;

	/**
	 * 创建单聊房间
	 *
	 * @param userIdList 用户列表, 必须有两个用户
	 * @return 房间ID
	 */
	@Override
	public Long createSingleChatRoom(List<Long> userIdList) {
		ThrowUtil.tif(CollUtil.isEmpty(userIdList), "房间创建失败, 用户列表数量错误");
		ThrowUtil.tif(userIdList.size() != BaseConstant.TWO, "房间创建失败, 用户列表必须只有两个用户");
		// 排序
		List<Long> sorted = userIdList.stream().sorted().toList();
		Long roomId = null;
		// 生成房间Key
		String roomKey = sorted.stream().map(String::valueOf).collect(Collectors.joining(BaseConstant.SEPARATOR));
		// 查询房间是否存在, 存在且状态为禁用则恢复房间, 否则创建房间
		RoomFriend roomFriend = roomFriendDao.getRoomFriendByRoomKey(roomKey);
		if (ObjectUtil.isNotNull(roomFriend)) {
			if (RoomStatusEnum.DISABLED.getKey().equals(roomFriend.getRoomStatus())) {
				boolean result = roomFriendDao.restoreRoomFriend(roomFriend.getRoomId());
				ThrowUtil.tif(!result, "房间恢复失败");
			}
			roomId = roomFriend.getRoomId();
		} else {
			// 创建房间
			Room room = new Room()
					.setRoomType(RoomTypeEnum.SINGLE_CHAT.getKey())
					.setShowType(ShowTypeEnum.UN_ALL_STAFF.getKey());
			boolean result = roomDao.save(room);
			ThrowUtil.tif(!result, "房间创建失败");
			// 保存单聊房间信息
			roomFriend = new RoomFriend()
					.setRoomId(room.getRoomId())
					.setUserId1(sorted.get(0))
					.setUserId2(sorted.get(1))
					.setRoomKey(roomKey)
					.setRoomStatus(RoomStatusEnum.NORMAL.getKey());
			result = roomFriendDao.save(roomFriend);
			ThrowUtil.tif(!result, "房间创建失败");
			roomId = room.getRoomId();
		}
		return roomId;
	}

	/**
	 * 禁用单聊房间
	 *
	 * @param userIdList 用户列表, 必须有两个用户
	 */
	@Override
	public void disableFriendRoom(List<Long> userIdList) {
		// 排序
		List<Long> sorted = userIdList.stream().sorted().toList();
		// 生成房间Key
		String roomKey = sorted.stream().map(String::valueOf).collect(Collectors.joining(BaseConstant.SEPARATOR));
		boolean result = roomFriendDao.disableFriendRoom(roomKey);
		ThrowUtil.tif(!result, "禁用失败");
	}
}
