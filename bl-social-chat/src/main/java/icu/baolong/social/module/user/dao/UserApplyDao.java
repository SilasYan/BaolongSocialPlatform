package icu.baolong.social.module.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.module.user.enums.ApplyStatusEnum;
import icu.baolong.social.module.user.enums.ApplyTypeEnum;
import icu.baolong.social.module.user.enums.ReadStatusEnum;
import icu.baolong.social.repository.user.entity.UserApply;
import icu.baolong.social.repository.user.mapper.UserApplyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户申请表 (user_friend_apply) - 持久化服务
 *
 * @author Baolong 2025-05-30 23:54:59
 */
@Repository
public class UserApplyDao extends ServiceImpl<UserApplyMapper, UserApply> {

	/**
	 * 获取待审批的好友申请记录
	 *
	 * @param userId     申请人ID
	 * @param targetUser 接收人ID
	 * @return 好友申请记录
	 */
	public UserApply getUnapprovedFriendApplyRecord(Long userId, Long targetUser) {
		return this.lambdaQuery()
				.eq(UserApply::getApplyUser, userId)
				.eq(UserApply::getTargetUser, targetUser)
				.eq(UserApply::getApplyType, ApplyTypeEnum.ADD_FRIEND.getKey())
				.eq(UserApply::getApplyStatus, ApplyStatusEnum.UNAPPROVED.getKey())
				.one();
	}

	/**
	 * 更新好友申请记录状态
	 *
	 * @param applyId     申请记录ID
	 * @param applyStatus 申请状态
	 * @return 是否成功
	 */
	public boolean changeApplyStatus(Long applyId, Integer applyStatus) {
		return this.lambdaUpdate()
				.set(UserApply::getApplyStatus, applyStatus)
				.eq(UserApply::getApplyId, applyId)
				.update();
	}

	/**
	 * 获取未读的好友申请记录数
	 *
	 * @param targetUser 接收人ID
	 * @return 未读的好友申请记录数
	 */
	public Long getTargetUserUnreadCount(Long targetUser) {
		return this.lambdaQuery()
				.eq(UserApply::getTargetUser, targetUser)
				.eq(UserApply::getReadStatus, ReadStatusEnum.UNREAD.getLabel())
				.count();
	}

	/**
	 * 获取好友申请列表, 普通分页
	 *
	 * @param userId 接收人ID
	 * @param page   分页对象
	 * @return 好友申请分页列表
	 */
	public Page<UserApply> getFriendApplyListAsPage(Long userId, Page<UserApply> page) {
		return this.lambdaQuery()
				.eq(UserApply::getTargetUser, userId)
				.eq(UserApply::getApplyType, ApplyTypeEnum.ADD_FRIEND.getKey())
				.orderByDesc(UserApply::getCreateTime)
				.page(page);
	}

	/**
	 * 获取好友申请未读数
	 *
	 * @param userId 接收人ID
	 * @return 好友申请未读数
	 */
	public Long getFriendApplyUnreadCount(Long userId) {
		return this.lambdaQuery()
				.eq(UserApply::getTargetUser, userId)
				.eq(UserApply::getReadStatus, ReadStatusEnum.UNREAD.getLabel())
				.count();
	}

	/**
	 * 标记好友申请为已读
	 *
	 * @param applyIdList 好友申请ID列表
	 */
	public void readApplyList(List<Long> applyIdList) {
		this.lambdaUpdate()
				.set(UserApply::getReadStatus, ReadStatusEnum.READ.getKey())
				.eq(UserApply::getReadStatus, ReadStatusEnum.UNREAD.getKey())
				.in(UserApply::getApplyId, applyIdList)
				.update();
	}
}
