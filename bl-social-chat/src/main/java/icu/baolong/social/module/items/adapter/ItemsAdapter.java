package icu.baolong.social.module.items.adapter;

import icu.baolong.social.module.items.domain.response.ItemsResp;
import icu.baolong.social.repository.items.entity.Items;

import java.util.List;
import java.util.Optional;

/**
 * 物品适配器
 *
 * @author Silas Yan 2025-06-01 18:17
 */
public class ItemsAdapter {

	public static ItemsResp buildItemsResp(Items items) {
		return ItemsResp.builder()
				.itemId(items.getItemId())
				.itemType(items.getItemType())
				.itemName(items.getItemName())
				.itemDesc(items.getItemDesc())
				.itemImage(items.getItemImage())
				.build();
	}

	public static List<ItemsResp> buildItemsRespList(List<Items> itemsList) {
		return Optional.ofNullable(itemsList)
				.orElse(List.of())
				.stream()
				.map(ItemsAdapter::buildItemsResp)
				.toList();
	}
}
