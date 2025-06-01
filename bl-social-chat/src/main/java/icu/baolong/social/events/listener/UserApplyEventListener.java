package icu.baolong.social.events.listener;

import icu.baolong.social.events.UserApplyEventPublisher;
import icu.baolong.social.module.user.dao.UserApplyDao;
import icu.baolong.social.repository.user.entity.UserApply;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 用户申请事件监听者
 *
 * @author Silas Yan 2025-05-26 21:00
 */
@Component
public class UserApplyEventListener {
	@Resource
	private UserApplyDao userApplyDao;

	/**
	 * 监听用户申请, 通知用户
	 *
	 * @param event 事件对象
	 */
	@Async
	@TransactionalEventListener(classes = UserApplyEventPublisher.class, fallbackExecution = true)
	public void notifyUser(UserApplyEventPublisher event) {
		UserApply userApply = event.getUserApply();
		Long unreadCount = userApplyDao.getTargetUserUnreadCount(userApply.getTargetUser());
		// TODO 推送
	}
}
