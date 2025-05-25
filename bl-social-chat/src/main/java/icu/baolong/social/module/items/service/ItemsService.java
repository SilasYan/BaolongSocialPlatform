package icu.baolong.social.module.items.service;

import icu.baolong.social.module.items.domain.response.ItemsResp;
import icu.baolong.social.repository.items.entity.Items;

import java.util.List;

/**
 * 物品表 (items) - 服务接口
 *
 * @author Baolong 2025-05-25 15:01:58
 */
public interface ItemsService {

	/**
	 * 根据物品类型获取物品列表
	 *
	 * @param itemType 物品类型
	 * @return 物品列表
	 */
	List<ItemsResp> getItemsListByType(Integer itemType);

	/**
	 * 根据物品ID获取物品信息
	 *
	 * @param itemId 物品ID
	 * @return 物品信息
	 */
	Items getItemsById(Long itemId);
}
