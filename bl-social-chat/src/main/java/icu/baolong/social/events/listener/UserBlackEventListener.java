package icu.baolong.social.events.listener;

import icu.baolong.social.events.UserBlackEventPublisher;
import icu.baolong.social.service.websocket.adapter.WSAdapter;
import icu.baolong.social.service.websocket.service.WebSocketService;
import icu.baolong.social.module.user.dao.UserDao;
import icu.baolong.social.module.user.enums.UserDisabledEnum;
import icu.baolong.social.repository.user.entity.User;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 用户拉黑事件监听者
 *
 * @author Silas Yan 2025-05-26 21:00
 */
@Component
public class UserBlackEventListener {
	@Resource
	private UserDao userDao;
	@Resource
	private WebSocketService webSocketService;

	/**
	 * 监听用户拉黑, 禁用用户
	 *
	 * @param event 事件对象
	 */
	@Async
	@TransactionalEventListener(classes = UserBlackEventPublisher.class, phase = TransactionPhase.AFTER_COMMIT)
	public void disableUser(UserBlackEventPublisher event) {
		// 修改用户的状态为禁用
		User user = event.getUser();
		User updateUser = new User();
		updateUser.setUserId(user.getUserId());
		updateUser.setIsDisabled(UserDisabledEnum.DISABLED.getKey());
		userDao.updateById(updateUser);
		// 通知所有在线用户
		webSocketService.pushToAllOnline(WSAdapter.buildBlackUserResp(user.getUserId()));
	}
}
