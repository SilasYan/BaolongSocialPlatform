package icu.baolong.social.events.listener;

import icu.baolong.social.events.UserOfflineEventPublisher;
import icu.baolong.social.module.user.dao.UserDao;
import icu.baolong.social.module.user.enums.OnlineStatusEnum;
import icu.baolong.social.repository.user.entity.User;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 用户下线事件监听者
 *
 * @author Silas Yan 2025-05-26 21:00
 */
@Component
public class UserOfflineEventListener {
	@Resource
	private UserDao userDao;

	/**
	 * 监听用户下线,更新用户在线状态
	 *
	 * @param event 事件对象
	 */
	@Async
	@EventListener(classes = UserOfflineEventPublisher.class)
	public void refreshIp(UserOfflineEventPublisher event) {
		User user = event.getUser();
		user.setOnlineStatus(OnlineStatusEnum.OFFLINE.getKey());
		userDao.updateById(user);
	}
}
