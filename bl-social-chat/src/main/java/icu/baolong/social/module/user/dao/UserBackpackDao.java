package icu.baolong.social.module.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.module.user.domain.enums.ItemUseStatusEnum;
import icu.baolong.social.repository.user.entity.UserBackpack;
import icu.baolong.social.repository.user.mapper.UserBackpackMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 用户背包表 (user_backpack) - 持久化服务
 *
 * @author Baolong 2025-05-25 15:01:58
 */
@Repository
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack> {

	/**
	 * 获取一个有效地改名卡
	 *
	 * @param userId 用户ID
	 * @param itemId 物品ID
	 * @return 用户背包对象
	 */
	public UserBackpack getOneValidNameChangeCard(Long userId, Long itemId) {
		return this.lambdaQuery()
				.eq(UserBackpack::getUserId, userId)
				.eq(UserBackpack::getItemId, itemId)
				.eq(UserBackpack::getUseStatus, ItemUseStatusEnum.NOT_USE.getKey())
				.one();
	}

	/**
	 * 使用背包物品
	 *
	 * @param backpackId 背包ID
	 * @return 是否使用成功
	 */
	public boolean useBackpackItem(Long backpackId) {
		return this.lambdaUpdate()
				.eq(UserBackpack::getId, backpackId)
				.eq(UserBackpack::getUseStatus, ItemUseStatusEnum.NOT_USE.getKey())
				.set(UserBackpack::getUseStatus, ItemUseStatusEnum.USE.getKey())
				.set(UserBackpack::getUseTime, new Date())
				.update();
	}

	/**
	 * 根据用户ID和物品ID获取物品信息
	 *
	 * @param userId 用户ID
	 * @param itemId 物品ID
	 * @return 物品信息
	 */
	public UserBackpack getBadgeByUserIdAndItemId(Long userId, Long itemId) {
		return this.lambdaQuery()
				.eq(UserBackpack::getUserId, userId)
				.eq(UserBackpack::getItemId, itemId)
				.one();
	}

	/**
	 * 根据用户ID和物品ID列表获取徽章列表
	 *
	 * @param userId 用户ID
	 * @return 徽章列表
	 */
	public List<UserBackpack> getBadgeListByUserIdAndItemIds(Long userId, List<Long> itemIds) {
		return this.lambdaQuery()
				.eq(UserBackpack::getUserId, userId)
				.in(UserBackpack::getItemId, itemIds)
				.list();
	}

	/**
	 * 根据幂等号获取背包物品
	 *
	 * @param idempotent 幂等号
	 * @return 背包物品
	 */
	public UserBackpack getOneByIdempotent(String idempotent) {
		return this.lambdaQuery().eq(UserBackpack::getIdempotent, idempotent).one();
	}

	/**
	 * 根据用户ID和物品ID获取背包物品数量
	 *
	 * @param userId 用户ID
	 * @param itemId 物品ID
	 * @return 物品数量
	 */
	public Long getCountByItemId(Long userId, Long itemId) {
		return this.lambdaQuery()
				.eq(UserBackpack::getUserId, userId)
				.eq(UserBackpack::getItemId, itemId)
				.count();
	}
}
