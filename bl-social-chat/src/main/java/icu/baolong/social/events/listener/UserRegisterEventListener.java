package icu.baolong.social.events.listener;

import cn.hutool.json.JSONArray;
import icu.baolong.social.base.constants.CacheConstant;
import icu.baolong.social.base.constants.BusinessTypeEnum;
import icu.baolong.social.events.UserRegisterEventPublisher;
import icu.baolong.social.module.sys.service.SysConfigService;
import icu.baolong.social.module.user.service.UserBackpackService;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 用户注册事件监听者
 *
 * @author Silas Yan 2025-05-26 21:00
 */
@Component
public class UserRegisterEventListener {
	@Resource
	private UserBackpackService userBackpackService;
	@Resource
	private SysConfigService sysConfigService;

	/**
	 * 监听用户注册, 发放注册物品
	 *
	 * @param event 事件对象
	 */
	@Async
	@EventListener(classes = UserRegisterEventPublisher.class)
	public void distributeItem(UserRegisterEventPublisher event) {
		JSONArray itemsId = sysConfigService.getConfigAsJsonArr(CacheConstant.REGISTER_ITEMS);
		for (Object o : itemsId) {
			Long itemId = Long.parseLong(o.toString());
			userBackpackService.distributeItems(event.getUser().getUserId(), itemId, BusinessTypeEnum.REGISTER.getKey());
		}
	}
}
