package icu.baolong.social.module.items.service.impl;

import icu.baolong.social.cache.ItemsCache;
import icu.baolong.social.module.items.adapter.ItemsAdapter;
import icu.baolong.social.module.items.domain.response.ItemsResp;
import icu.baolong.social.module.items.service.ItemsService;
import icu.baolong.social.repository.items.entity.Items;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物品表（items） - 服务实现
 *
 * @author Silas Yan 2025-05-25 15:19
 */
@Service
@RequiredArgsConstructor
public class ItemsServiceImpl implements ItemsService {

	private final ItemsCache itemsCache;

	/**
	 * 根据物品类型获取物品列表
	 *
	 * @param itemType 物品类型
	 * @return 物品列表
	 */
	@Override
	public List<ItemsResp> getItemsListByType(Integer itemType) {
		List<Items> itemsList = itemsCache.getItemsListByType(itemType);
		return ItemsAdapter.buildItemsRespList(itemsList);
	}

	/**
	 * 根据物品ID获取物品信息
	 *
	 * @param itemId 物品ID
	 * @return 物品信息
	 */
	@Override
	public Items getItemsById(Long itemId) {
		return itemsCache.getItemsById(itemId);
	}
}
