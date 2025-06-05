package icu.baolong.social.cache;

import cn.hutool.core.util.ObjectUtil;
import icu.baolong.social.base.constants.KeyConstant;
import icu.baolong.social.common.utils.RedisUtil;
import icu.baolong.social.module.room.dao.RoomGroupDao;
import icu.baolong.social.module.room.dao.RoomGroupMemberDao;
import icu.baolong.social.repository.room.entity.RoomGroup;
import icu.baolong.social.repository.room.entity.RoomGroupMember;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 房间缓存
 *
 * @author Silas Yan 2025-06-05 20:57
 */
@Slf4j
@Component
@CacheConfig(cacheNames = "room")
public class RoomCache {

	@Resource
	private RedisUtil redisUtil;
	@Resource
	private RoomGroupDao roomGroupDao;
	@Resource
	private RoomGroupMemberDao roomGroupMemberDao;

	/**
	 * 房间排序
	 *
	 * @param roomId      房间ID
	 * @param roomNewTime 房间最新时间
	 */
	public void sortRoom(Long roomId, Date roomNewTime) {
		redisUtil.zsSet(KeyConstant.PREFIX_ROOM_SORT, roomId, roomNewTime.getTime());
	}

	/**
	 * 获取群聊成员列表
	 *
	 * @param roomId 房间ID
	 * @return 群聊成员列表
	 */
	@Cacheable(cacheNames = "member", key = "'groupMember' + #p0")
	public List<Long> getRoomGroupMemberList(Long roomId) {
		RoomGroup roomGroup = roomGroupDao.getRoomGroupByRoomId(roomId);
		if (ObjectUtil.isNotNull(roomGroup)) {
			List<RoomGroupMember> roomGroupMemberList = roomGroupMemberDao.getRoomGroupMemberByGroupId(roomGroup.getGroupId());
			return roomGroupMemberList.stream().map(RoomGroupMember::getUserId).toList();
		}
		return null;
	}
}
