package icu.baolong.social.events.listener;

import cn.hutool.json.JSONArray;
import icu.baolong.social.entity.constants.CacheConstant;
import icu.baolong.social.entity.enums.BusinessTypeEnum;
import icu.baolong.social.events.UserOnlineEventPublisher;
import icu.baolong.social.events.UserRegisterEventPublisher;
import icu.baolong.social.manager.ip.IpManager;
import icu.baolong.social.module.sys.service.SysConfigService;
import icu.baolong.social.module.user.dao.UserDao;
import icu.baolong.social.module.user.domain.enums.OnlineStatusEnum;
import icu.baolong.social.module.user.service.UserBackpackService;
import icu.baolong.social.repository.user.entity.User;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 用户事件监听者
 *
 * @author Silas Yan 2025-05-26 21:00
 */
@Component
public class UserEventListener {

	@Resource
	private UserBackpackService userBackpackService;
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private UserDao userDao;
	@Resource
	private IpManager ipManager;

	@Async
	@EventListener(classes = UserRegisterEventPublisher.class)
	public void distributeItem(UserRegisterEventPublisher userEvent) {
		JSONArray itemsId = sysConfigService.getConfigAsJsonArr(CacheConstant.REGISTER_ITEMS);
		for (Object o : itemsId) {
			Long itemId = Long.parseLong(o.toString());
			userBackpackService.distributeItems(userEvent.getUser().getId(), itemId, BusinessTypeEnum.REGISTER.getKey());
		}
	}

	@Async
	@EventListener(classes = UserOnlineEventPublisher.class)
	public void refreshIp(UserOnlineEventPublisher userEvent) {
		User user = userEvent.getUser();
		User updateUser = new User();
		updateUser.setId(user.getId());
		updateUser.setLastLoginTime(user.getLastLoginTime());
		updateUser.setIpInfo(user.getIpInfo());
		updateUser.setOnlineStatus(OnlineStatusEnum.ONLINE.getKey());
		userDao.updateById(updateUser);
		// 刷新IP信息
		ipManager.refreshIpInfo(user.getId(), 3);
	}
}
