package icu.baolong.social.events.listener;

import cn.hutool.json.JSONArray;
import icu.baolong.social.entity.constants.CacheConstant;
import icu.baolong.social.entity.enums.BusinessTypeEnum;
import icu.baolong.social.events.UserEventPublisher;
import icu.baolong.social.module.sys.service.SysConfigService;
import icu.baolong.social.module.user.service.UserBackpackService;
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

	@Async
	@EventListener(classes = UserEventPublisher.class)
	public void distributeItem(UserEventPublisher userEvent) {
		JSONArray itemsId = sysConfigService.getConfigAsJsonArr(CacheConstant.REGISTER_ITEMS);
		for (Object o : itemsId) {
			Long itemId = Long.parseLong(o.toString());
			userBackpackService.distributeItems(userEvent.getUser().getId(), itemId, BusinessTypeEnum.REGISTER.getKey());
		}
	}
}
