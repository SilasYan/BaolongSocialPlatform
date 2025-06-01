package icu.baolong.social.module.friend.service;

import icu.baolong.social.common.base.page.CursorRequest;
import icu.baolong.social.common.base.page.CursorResponse;
import icu.baolong.social.common.base.page.PageResponse;
import icu.baolong.social.module.friend.domain.request.FriendApplyQueryReq;
import icu.baolong.social.module.friend.domain.request.FriendAddApplyReq;
import icu.baolong.social.module.friend.domain.response.FriendApplyResp;
import icu.baolong.social.module.friend.domain.response.FriendCheckResp;
import icu.baolong.social.module.friend.domain.response.FriendResp;

import java.util.List;

/**
 * 用户好友表 (user_friend) - 服务接口
 *
 * @author Baolong 2025-05-30 23:54:59
 */
public interface FriendService {

	/**
	 * 获取好友列表, 游标分页
	 *
	 * @param cursorRequest 游标分页请求
	 * @return 好友分页列表
	 */
	CursorResponse<FriendResp> getFriendListAsCursorPage(CursorRequest cursorRequest);

	/**
	 * 好友添加申请
	 *
	 * @param userId            登录用户ID
	 * @param friendAddApplyReq 好友添加申请请求
	 */
	void friendAddApply(Long userId, FriendAddApplyReq friendAddApplyReq);

	/**
	 * 审批好友申请
	 *
	 * @param userId        登录用户ID
	 * @param applyId       申请记录ID
	 * @param approveStatus 审批状态（1-同意、2-拒绝）
	 */
	void approveFriendApply(Long userId, Long applyId, Integer approveStatus);

	/**
	 * 获取好友申请列表, 普通分页
	 *
	 * @param userId              登录用户ID
	 * @param friendApplyQueryReq 好友申请查询请求
	 * @return 好友申请分页列表
	 */
	PageResponse<FriendApplyResp> getFriendApplyListAsPage(Long userId, FriendApplyQueryReq friendApplyQueryReq);

	/**
	 * 获取好友申请未读数量
	 *
	 * @param userId 登录用户ID
	 * @return 好友申请未读数量
	 */
	Long getFriendApplyUnreadCount(Long userId);

	/**
	 * 判断用户是否是好友, 批量
	 *
	 * @param userId     登录用户ID
	 * @param userIdList 用户ID列表
	 * @return 判断后对象列表
	 */
	List<FriendCheckResp> getCheckIsMyFriend(Long userId, List<Long> userIdList);

	/**
	 * 删除好友
	 *
	 * @param userId   登录用户ID
	 * @param friendId 好友ID
	 */
	void deleteFriend(Long userId, Long friendId);
}
