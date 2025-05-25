package icu.baolong.social.cache;

import icu.baolong.social.module.items.dao.ItemsDao;
import icu.baolong.social.repository.items.entity.Items;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 物品缓存
 *
 * @author Silas Yan 2025-05-25 16:04
 */
@Component
@CacheConfig(cacheNames = "items")
public class ItemsCache {

	@Resource
	private ItemsDao itemsDao;

	/**
	 * 获取物品列表
	 *
	 * @param itemType 物品类型
	 * @return 物品列表
	 */
	@Cacheable(key = "'items:' + #p0")
	public List<Items> getItemsListByType(Integer itemType) {
		System.out.println("从数据库获取物品列表: " + itemType);
		return itemsDao.getItemsByType(itemType);
	}

	/**
	 * 获取物品详情
	 *
	 * @param itemId 物品ID
	 * @return 物品详情
	 */
	@Cacheable(key = "'item:' + #p0")
	public Items getItemsById(Long itemId) {
		return itemsDao.getItemById(itemId);
	}

}
