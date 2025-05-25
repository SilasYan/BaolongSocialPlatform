package icu.baolong.social.controller;

import icu.baolong.social.common.exception.ThrowUtil;
import icu.baolong.social.common.response.BaseResponse;
import icu.baolong.social.common.response.Result;
import icu.baolong.social.module.items.domain.enums.ItemTypeEnum;
import icu.baolong.social.module.items.domain.request.ItemsQueryReq;
import icu.baolong.social.module.items.domain.response.ItemsResp;
import icu.baolong.social.module.items.service.ItemsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 物品控制器
 *
 * @author Silas Yan 2025-05-25 16:16
 */
@Tag(name = "物品接口", description = "物品相关业务接口")
@RestController
@RequestMapping("/api/items")
public class ItemsController {
	@Resource
	private ItemsService itemsService;

	@Operation(summary = "根据物品类型获取物品列表", description = "根据物品类型获取物品列表")
	@GetMapping("/listByType")
	public BaseResponse<List<ItemsResp>> getItemsByType(ItemsQueryReq itemsQueryReq) {
		ThrowUtil.tif(!ItemTypeEnum.contains(itemsQueryReq.getItemType()), "物品类型错误");
		return Result.success(itemsService.getItemsListByType(itemsQueryReq.getItemType()));
	}
}
