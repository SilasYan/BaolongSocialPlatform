package icu.baolong.social.module.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import icu.baolong.social.cache.ItemsCache;
import icu.baolong.social.common.function.lock.RedissonLock;
import icu.baolong.social.module.sys.domain.enums.RepeatStatusEnum;
import icu.baolong.social.module.user.dao.UserBackpackDao;
import icu.baolong.social.module.user.service.UserBackpackService;
import icu.baolong.social.repository.items.entity.Items;
import icu.baolong.social.repository.user.entity.UserBackpack;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

/**
 * 用户背包表 (user_backpack) - 服务实现
 *
 * @author Silas Yan 2025-05-25 15:21
 */
@Service
@RequiredArgsConstructor
public class UserBackpackServiceImpl implements UserBackpackService {

	private final UserBackpackDao userBackpackDao;
	private final ItemsCache itemsCache;

	/**
	 * 发放物品
	 *
	 * @param userId       用户ID
	 * @param itemId       物品ID
	 * @param businessType 业务类型
	 */
	@Override
	public void distributeItems(Long userId, Long itemId, String businessType) {
		// 幂等号
		String idempotent = String.format("%d_%d_%s", userId, itemId, businessType);
		// 处理发放物品
		((UserBackpackServiceImpl) AopContext.currentProxy()).handleDistributeItems(userId, itemId, idempotent);
	}

	/**
	 * 处理发放物品
	 *
	 * @param userId     用户ID
	 * @param itemId     物品ID
	 * @param idempotent 幂等号
	 */
	@RedissonLock(key = "#idempotent", waitTime = 5000)
	public void handleDistributeItems(Long userId, Long itemId, String idempotent) {
		// 获取当前幂等号是否存在, 存在不发放
		UserBackpack userBackpack = userBackpackDao.getOneByIdempotent(idempotent);
		if (ObjectUtil.isNotNull(userBackpack)) {
			return;
		}
		// 判断当前物品是否允许重复发放
		Items items = itemsCache.getItemsById(itemId);
		if (!RepeatStatusEnum.isRepeat(items.getRepeatStatus())) {
			Long count = userBackpackDao.getCountByItemId(userId, itemId);
			if (count > 0) {
				return;
			}
		}
		// 发放物品
		userBackpack = new UserBackpack()
				.setUserId(userId)
				.setItemId(itemId)
				.setIdempotent(idempotent);
		userBackpackDao.save(userBackpack);
	}
}
