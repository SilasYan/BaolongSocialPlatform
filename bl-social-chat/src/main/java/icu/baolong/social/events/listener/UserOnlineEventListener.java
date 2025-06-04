package icu.baolong.social.events.listener;

import icu.baolong.social.events.UserOnlineEventPublisher;
import icu.baolong.social.manager.ip.IpManager;
import icu.baolong.social.module.user.dao.UserDao;
import icu.baolong.social.module.user.enums.OnlineStatusEnum;
import icu.baolong.social.repository.user.entity.User;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 用户上线事件监听者
 *
 * @author Silas Yan 2025-05-26 21:00
 */
@Component
public class UserOnlineEventListener {
	@Resource
	private UserDao userDao;
	@Resource
	private IpManager ipManager;

	/**
	 * 监听用户上线, 刷新IP信息
	 *
	 * @param event 事件对象
	 */
	@Async
	@EventListener(classes = UserOnlineEventPublisher.class)
	public void refreshIp(UserOnlineEventPublisher event) {
		User user = event.getUser();
		User updateUser = new User();
		updateUser.setUserId(user.getUserId());
		updateUser.setLastLoginTime(user.getLastLoginTime());
		updateUser.setIpInfo(user.getIpInfo());
		updateUser.setOnlineStatus(OnlineStatusEnum.ONLINE.getKey());
		userDao.updateById(updateUser);
		// 刷新IP信息
		ipManager.refreshIpInfo(user.getUserId(), 3);
	}
}
