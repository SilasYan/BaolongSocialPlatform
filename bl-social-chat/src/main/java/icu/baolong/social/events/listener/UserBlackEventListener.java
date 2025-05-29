package icu.baolong.social.events.listener;

import icu.baolong.social.events.UserBlackPublisher;
import icu.baolong.social.manager.websocket.adapter.WSAdapter;
import icu.baolong.social.manager.websocket.service.WebSocketService;
import icu.baolong.social.module.user.dao.UserDao;
import icu.baolong.social.module.user.domain.enums.UserDisabledEnum;
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
	 * @param userEvent 用户事件
	 */
	@Async
	@TransactionalEventListener(classes = UserBlackPublisher.class, phase = TransactionPhase.AFTER_COMMIT)
	public void disableUser(UserBlackPublisher userEvent) {
		// 修改用户的状态为禁用
		User user = userEvent.getUser();
		User updateUser = new User();
		updateUser.setId(user.getId());
		updateUser.setIsDisabled(UserDisabledEnum.DISABLED.getKey());
		userDao.updateById(updateUser);
		// 通知所有在线用户
		webSocketService.pushToAllOnline(WSAdapter.buildBlackUserResp(user.getId()));
	}
}
