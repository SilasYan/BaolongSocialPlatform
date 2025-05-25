package icu.baolong.social.module.items.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.repository.items.entity.Items;
import icu.baolong.social.repository.items.mapper.ItemsMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 物品表 (items) - 持久化服务
 *
 * @author Baolong 2025-05-25 15:01:58
 */
@Repository
public class ItemsDao extends ServiceImpl<ItemsMapper, Items> {

	/**
	 * 获取物品列表
	 *
	 * @param itemType 物品类型
	 * @return 物品列表
	 */
	public List<Items> getItemsByType(Integer itemType) {
		return this.lambdaQuery().eq(Items::getItemType, itemType).list();
	}

	/**
	 * 获取物品详情
	 *
	 * @param itemId 物品ID
	 * @return 物品详情
	 */
	public Items getItemById(Long itemId) {
		return this.lambdaQuery().eq(Items::getId, itemId).one();
	}
}
