package icu.baolong.social.module.friend.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.common.base.page.CursorRequest;
import icu.baolong.social.common.base.page.CursorResponse;
import icu.baolong.social.common.utils.CursorUtil;
import icu.baolong.social.repository.friend.entity.Friend;
import icu.baolong.social.repository.friend.mapper.FriendMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 好友表 (friend) - 持久化服务
 *
 * @author Baolong 2025-05-30 23:54:59
 */
@Repository
public class FriendDao extends ServiceImpl<FriendMapper, Friend> {

	/**
	 * 获取好友列表, 游标分页
	 *
	 * @param cursorRequest 游标分页请求
	 * @return 好友分页列表
	 */
	public CursorResponse<Friend> getFriendListAsCursorPage(Long userId, CursorRequest cursorRequest) {
		return CursorUtil.buildAndExecute(
				this,
				cursorRequest,
				wrapper -> wrapper.eq(Friend::getMyId, userId),
				Friend::getId);
	}

	/**
	 * 根据我的ID和好友ID获取好友关联信息
	 *
	 * @param userId     我的ID
	 * @param targetUser 好友ID
	 * @return 好友关联信息
	 */
	public Friend getFriendByMyIdAndFriendId(Long userId, Long targetUser) {
		return this.lambdaQuery()
				.eq(Friend::getMyId, userId)
				.eq(Friend::getFriendId, targetUser)
				.one();
	}

	/**
	 * 根据我的ID和好友ID列表获取好友关联信息
	 *
	 * @param userId     我的ID
	 * @param userIdList 好友ID列表
	 * @return 好友关联信息
	 */
	public List<Friend> getFriendListByMyIdAndFriendIds(Long userId, List<Long> userIdList) {
		return this.lambdaQuery()
				.eq(Friend::getMyId, userId)
				.in(Friend::getFriendId, userIdList)
				.list();
	}

	/**
	 * 根据我的ID和好友ID获取好友关系列表
	 *
	 * @param userId     我的ID
	 * @param targetUser 好友ID
	 * @return 好友关系列表
	 */
	public List<Friend> getFriendRelationByMyIdAndFriendId(Long userId, Long targetUser) {
		return this.lambdaQuery()
				.eq(Friend::getMyId, userId)
				.eq(Friend::getFriendId, targetUser)
				.or()
				.eq(Friend::getMyId, targetUser)
				.eq(Friend::getFriendId, userId)
				.list();
	}
}
