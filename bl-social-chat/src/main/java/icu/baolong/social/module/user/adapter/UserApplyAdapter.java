package icu.baolong.social.module.user.adapter;

import icu.baolong.social.module.user.enums.ApplyStatusEnum;
import icu.baolong.social.module.user.enums.ApplyTypeEnum;
import icu.baolong.social.module.user.enums.ReadStatusEnum;
import icu.baolong.social.repository.user.entity.UserApply;

/**
 * 用户申请适配器
 *
 * @author Silas Yan 2025-05-31 02:34
 */
public class UserApplyAdapter {

	/**
	 * 构建好友申请对象
	 *
	 * @param applyUser    申请人ID
	 * @param targetUser   接收人ID
	 * @param applyContent 申请内容
	 * @return 好友申请对象
	 */
	public static UserApply buildUserApply(Long applyUser, Long targetUser, String applyContent) {
		return new UserApply()
				.setApplyUser(applyUser)
				.setApplyType(ApplyTypeEnum.ADD_FRIEND.getKey())
				.setTargetUser(targetUser)
				.setApplyContent(applyContent)
				.setApplyStatus(ApplyStatusEnum.UNAPPROVED.getKey())
				.setReadStatus(ReadStatusEnum.UNREAD.getKey());
	}
}
