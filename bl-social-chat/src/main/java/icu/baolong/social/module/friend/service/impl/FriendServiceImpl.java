package icu.baolong.social.module.friend.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import icu.baolong.social.common.exception.ThrowUtil;
import icu.baolong.social.function.lock.RedissonLock;
import icu.baolong.social.base.page.CursorRequest;
import icu.baolong.social.base.page.CursorResponse;
import icu.baolong.social.base.page.PageResponse;
import icu.baolong.social.events.UserApplyEventPublisher;
import icu.baolong.social.module.friend.adapter.FriendAdapter;
import icu.baolong.social.module.friend.dao.FriendDao;
import icu.baolong.social.module.friend.domain.request.FriendApplyQueryReq;
import icu.baolong.social.module.friend.domain.request.FriendAddApplyReq;
import icu.baolong.social.module.friend.domain.response.FriendApplyResp;
import icu.baolong.social.module.friend.domain.response.FriendCheckResp;
import icu.baolong.social.module.friend.domain.response.FriendResp;
import icu.baolong.social.module.friend.service.FriendService;
import icu.baolong.social.module.room.service.RoomService;
import icu.baolong.social.module.user.adapter.UserApplyAdapter;
import icu.baolong.social.module.user.dao.UserApplyDao;
import icu.baolong.social.module.user.dao.UserDao;
import icu.baolong.social.module.user.enums.ApplyStatusEnum;
import icu.baolong.social.repository.friend.entity.Friend;
import icu.baolong.social.repository.user.entity.User;
import icu.baolong.social.repository.user.entity.UserApply;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 用户好友表 (user_friend) - 服务实现
 *
 * @author Baolong 2025-05-30 23:54:59
 */
@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

	private final FriendDao friendDao;
	private final UserDao userDao;
	private final UserApplyDao userApplyDao;
	private final RoomService roomService;
	private final ApplicationEventPublisher eventPublisher;

	/**
	 * 获取好友列表, 游标分页
	 *
	 * @param cursorRequest 游标分页请求
	 * @return 好友分页列表
	 */
	@Override
	public CursorResponse<FriendResp> getFriendListAsCursorPage(CursorRequest cursorRequest) {
		long userId = StpUtil.getLoginIdAsLong();
		// 获取好友列表, 游标分页
		CursorResponse<Friend> friendCursorPage = friendDao.getFriendListAsCursorPage(userId, cursorRequest);
		List<Friend> friendList = friendCursorPage.getRecords();
		if (CollUtil.isEmpty(friendList)) {
			return CursorResponse.empty();
		}
		// 查询所有好友信息
		List<Long> friendIdList = friendList.stream().map(Friend::getFriendId).toList();
		List<User> userList = userDao.getUserBaseInfoListByUserIdList(friendIdList);
		// 构建好友响应对象列表
		List<FriendResp> friendRespList = FriendAdapter.buildFriendRespList(friendList, userList);
		// 构建路由分页响应对象
		return CursorResponse.build(friendCursorPage, friendRespList);
	}

	/**
	 * 好友添加申请
	 *
	 * @param userId            登录用户ID
	 * @param friendAddApplyReq 好友添加申请请求
	 */
	@RedissonLock(key = "#userId")
	@Override
	public void friendAddApply(Long userId, FriendAddApplyReq friendAddApplyReq) {
		Long targetUser = friendAddApplyReq.getTargetUser();
		// 查询是否已经是好友
		Friend friend = friendDao.getFriendByMyIdAndFriendId(userId, targetUser);
		ThrowUtil.tif(ObjectUtil.isNotNull(friend), "你们已经是好友啦~");
		// 查询是否有待审批的申请记录, 我的发起的好友申请的记录
		UserApply myUserApply = userApplyDao.getUnapprovedFriendApplyRecord(userId, targetUser);
		ThrowUtil.tif(ObjectUtil.isNotNull(myUserApply), "已经申请过了, 请耐心等待对方通过~");
		// 查询是否有待审批的申请记录, 对方发送了好友申请的记录
		UserApply friendUserApply = userApplyDao.getUnapprovedFriendApplyRecord(targetUser, userId);
		if (ObjectUtil.isNotNull(friendUserApply)) {
			// 直接审核通过
			((FriendService) AopContext.currentProxy())
					.approveFriendApply(userId, friendUserApply.getApplyId(), ApplyStatusEnum.AGREE.getKey());
			return;
		}
		// 保存好友申请记录
		UserApply userApply = UserApplyAdapter.buildUserApply(userId, targetUser, friendAddApplyReq.getApplyContent());
		boolean result = userApplyDao.save(userApply);
		ThrowUtil.tif(!result, "好友申请失败");
		// 发送一个申请事件, 通知对方
		eventPublisher.publishEvent(new UserApplyEventPublisher(this, userApply));
	}

	/**
	 * 审批好友申请
	 *
	 * @param userId        登录用户ID
	 * @param applyId       申请记录ID
	 * @param approveStatus 审批状态（1-同意、2-拒绝）
	 */
	@RedissonLock(key = "#userId")
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void approveFriendApply(Long userId, Long applyId, Integer approveStatus) {
		UserApply userApply = userApplyDao.getById(applyId);
		ThrowUtil.tif(ObjectUtil.isNull(userApply), "申请记录不存在");
		ThrowUtil.tif(!Objects.equals(userId, userApply.getTargetUser()), "申请记录不存在");
		ThrowUtil.tif(ApplyStatusEnum.AGREE.getKey().equals(userApply.getApplyStatus()), "已同意好友申请");
		ThrowUtil.tif(ApplyStatusEnum.REFUSE.getKey().equals(userApply.getApplyStatus()), "已拒绝好友申请");
		// 更新申请记录状态
		boolean result = userApplyDao.changeApplyStatus(userApply.getApplyId(), approveStatus);
		ThrowUtil.tif(!result, "审批好友申请失败");
		// 只有审批状态是同意才进行后续操作
		if (ApplyStatusEnum.AGREE.getKey().equals(approveStatus)) {
			Long applyUser = userApply.getApplyUser();
			// 创建好友关系
			ArrayList<Friend> friends = Lists.newArrayList(
					new Friend().setMyId(userId).setFriendId(applyUser),
					new Friend().setMyId(applyUser).setFriendId(userId)
			);
			result = friendDao.saveBatch(friends);
			ThrowUtil.tif(!result, "审批好友申请失败");
			// 创建单聊房间
			Long roomId = roomService.createSingleChatRoom(Arrays.asList(userId, applyUser));
			// TODO 发送一条消息: 我们已经是好友啦~
		}
	}

	/**
	 * 获取好友申请列表, 普通分页
	 *
	 * @param userId              登录用户ID
	 * @param friendApplyQueryReq 好友申请查询请求
	 * @return 好友申请分页列表
	 */
	@Override
	public PageResponse<FriendApplyResp> getFriendApplyListAsPage(Long userId, FriendApplyQueryReq friendApplyQueryReq) {
		Page<UserApply> userApplyPage = userApplyDao.getFriendApplyListAsPage(userId, friendApplyQueryReq.page(UserApply.class));
		// 获取申请人的信息
		if (CollUtil.isEmpty(userApplyPage.getRecords())) {
			return PageResponse.empty(userApplyPage, FriendApplyResp.class);
		}
		List<Long> applyUserIds = userApplyPage.getRecords().stream().map(UserApply::getApplyUser).toList();
		List<User> userList = userDao.getUserBaseInfoListByUserIdList(applyUserIds);
		// 设置已读
		userApplyDao.readApplyList(userApplyPage.getRecords().stream().map(UserApply::getApplyId).toList());
		return PageResponse.to(userApplyPage, userList, FriendApplyResp.class);
	}

	/**
	 * 获取好友申请未读数量
	 *
	 * @param userId 登录用户ID
	 * @return 好友申请未读数量
	 */
	@Override
	public Long getFriendApplyUnreadCount(Long userId) {
		return userApplyDao.getFriendApplyUnreadCount(userId);
	}

	/**
	 * 判断用户是否是好友, 批量
	 *
	 * @param userId     登录用户ID
	 * @param userIdList 用户ID列表
	 * @return 判断后对象列表
	 */
	@Override
	public List<FriendCheckResp> getCheckIsMyFriend(Long userId, List<Long> userIdList) {
		List<Friend> friendList = friendDao.getFriendListByMyIdAndFriendIds(userId, userIdList);
		List<Long> friendIdList = friendList.stream().map(Friend::getFriendId).toList();
		return userIdList.stream()
				.map(uid -> FriendCheckResp.builder()
						.userId(uid)
						.isFriend(friendIdList.contains(uid))
						.build())
				.toList();
	}

	/**
	 * 删除好友
	 *
	 * @param userId   登录用户ID
	 * @param friendId 好友ID
	 */
	@Override
	public void deleteFriend(Long userId, Long friendId) {
		List<Friend> friends = friendDao.getFriendRelationByMyIdAndFriendId(userId, friendId);
		ThrowUtil.tif(CollUtil.isEmpty(friends), "你们不是好友哦~");
		// 删除好友关系
		boolean result = friendDao.removeByIds(friends.stream().map(Friend::getId).toList());
		ThrowUtil.tif(!result, "删除好友失败");
		// 禁用房间
		roomService.disableFriendRoom(Arrays.asList(userId, friendId));
	}
}
