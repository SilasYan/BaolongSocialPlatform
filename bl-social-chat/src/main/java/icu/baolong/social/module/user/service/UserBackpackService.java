package icu.baolong.social.module.user.service;

/**
 * 用户背包表 (user_backpack) - 服务接口
 *
 * @author Baolong 2025-05-25 15:01:58
 */
public interface UserBackpackService {

	/**
	 * 发放物品
	 *
	 * @param userId       用户ID
	 * @param itemId       物品ID
	 * @param businessType 业务类型
	 */
	void distributeItems(Long userId, Long itemId, String businessType);
}
