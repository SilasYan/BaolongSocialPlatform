package icu.baolong.social.module.room.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.room.entity.RoomGroupMember;
import icu.baolong.social.repository.room.mapper.RoomGroupMemberMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 群聊成员表 (room_group_member) - 持久化服务
 *
 * @author Baolong 2025-05-30 23:54:59
 */
@Repository
public class RoomGroupMemberDao extends ServiceImpl<RoomGroupMemberMapper, RoomGroupMember> {

	/**
	 * 根据用户ID和群聊ID获取群聊成员信息
	 *
	 * @param userId  用户ID
	 * @param groupId 群聊ID
	 * @return 群聊成员信息
	 */
	public RoomGroupMember getRoomGroupMemberByUserIdAndGroupId(Long userId, Long groupId) {
		return this.lambdaQuery()
				.eq(RoomGroupMember::getUserId, userId)
				.eq(RoomGroupMember::getGroupId, groupId)
				.one();
	}

	/**
	 * 根据群聊ID获取群聊成员信息
	 *
	 * @param groupId 群聊ID
	 * @return 群聊成员信息
	 */
	public List<RoomGroupMember> getRoomGroupMemberByGroupId(Long groupId) {
		return this.lambdaQuery()
				.eq(RoomGroupMember::getGroupId, groupId)
				.list();
	}
}
