package icu.baolong.social.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import icu.baolong.social.common.constants.BaseConstant;
import icu.baolong.social.common.exception.ThrowUtil;
import icu.baolong.social.common.base.page.CursorRequest;
import icu.baolong.social.common.base.page.CursorResponse;
import icu.baolong.social.common.base.page.PageResponse;
import icu.baolong.social.common.base.response.BaseResponse;
import icu.baolong.social.common.base.response.Result;
import icu.baolong.social.common.constants.TextConstant;
import icu.baolong.social.module.friend.domain.request.ApproveFriendApplyReq;
import icu.baolong.social.module.friend.domain.request.FriendApplyQueryReq;
import icu.baolong.social.module.friend.domain.request.FriendAddApplyReq;
import icu.baolong.social.module.friend.domain.request.FriendCheckReq;
import icu.baolong.social.module.friend.domain.request.FriendDeleteReq;
import icu.baolong.social.module.friend.domain.response.FriendApplyResp;
import icu.baolong.social.module.friend.domain.response.FriendCheckResp;
import icu.baolong.social.module.friend.domain.response.FriendResp;
import icu.baolong.social.module.friend.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 好友控制器
 *
 * @author Silas Yan 2025-05-31 01:08
 */
@Tag(name = "好友接口", description = "好友相关业务接口")
@RestController
@RequestMapping("/api/friend")
public class FriendController {
	@Resource
	private FriendService friendService;

	@Operation(summary = "获取好友列表, 游标分页", description = "获取好友列表, 游标分页")
	@GetMapping("/page")
	public BaseResponse<CursorResponse<FriendResp>> getFriendListAsCursorPage(CursorRequest cursorRequest) {
		ThrowUtil.tif(cursorRequest.getPageSize() > BaseConstant.ONW_HUNDRED, "每页最多100条");
		return Result.success(friendService.getFriendListAsCursorPage(cursorRequest));
	}

	@Operation(summary = "好友添加申请", description = "好友添加申请")
	@PostMapping("/apply")
	public BaseResponse<?> friendAddApply(@RequestBody FriendAddApplyReq friendAddApplyReq) {
		ThrowUtil.nullIf(friendAddApplyReq, TextConstant.ERROR_NULL_OBJECT);
		Long targetUser = friendAddApplyReq.getTargetUser();
		ThrowUtil.tif(ObjectUtil.isNull(targetUser) || targetUser <= BaseConstant.ZERO, "请选择要加的好友");
		friendService.friendAddApply(StpUtil.getLoginIdAsLong(), friendAddApplyReq);
		return Result.success();
	}

	@Operation(summary = "审批好友申请", description = "审批好友申请")
	@PostMapping("/approveApply")
	public BaseResponse<?> approveFriendApply(@RequestBody ApproveFriendApplyReq approveFriendApplyReq) {
		ThrowUtil.nullIf(approveFriendApplyReq, TextConstant.ERROR_NULL_OBJECT);
		Long applyId = approveFriendApplyReq.getApplyId();
		ThrowUtil.tif(ObjectUtil.isNull(applyId) || applyId <= BaseConstant.ZERO, "请选择要审批的好友申请");
		friendService.approveFriendApply(StpUtil.getLoginIdAsLong(), applyId, approveFriendApplyReq.getApproveStatus());
		return Result.success();
	}

	@Operation(summary = "获取好友申请列表, 普通分页", description = "获取好友申请列表, 普通分页")
	@GetMapping("/apply/page")
	public BaseResponse<PageResponse<FriendApplyResp>> getFriendApplyListAsPage(FriendApplyQueryReq friendApplyQueryReq) {
		return Result.success(friendService.getFriendApplyListAsPage(StpUtil.getLoginIdAsLong(), friendApplyQueryReq));
	}

	@Operation(summary = "获取好友申请未读数量", description = "获取好友申请未读数量")
	@GetMapping("/apply/unread")
	public BaseResponse<Long> getFriendApplyUnreadCount() {
		return Result.success(friendService.getFriendApplyUnreadCount(StpUtil.getLoginIdAsLong()));
	}

	@Operation(summary = "判断用户是否是好友, 批量", description = "判断用户是否是好友, 批量")
	@GetMapping("/check")
	public BaseResponse<List<FriendCheckResp>> getCheckIsMyFriend(FriendCheckReq friendCheckReq) {
		List<Long> userIdList = friendCheckReq.getUserIdList();
		ThrowUtil.tif(CollUtil.isEmpty(userIdList) || userIdList.size() > BaseConstant.ONW_HUNDRED, "校验数量过多");
		return Result.success(friendService.getCheckIsMyFriend(StpUtil.getLoginIdAsLong(), userIdList));
	}

	@Operation(summary = "删除好友", description = "删除好友")
	@PostMapping("/delete")
	public BaseResponse<?> deleteFriend(@RequestBody FriendDeleteReq friendDeleteReq) {
		ThrowUtil.nullIf(friendDeleteReq, TextConstant.ERROR_NULL_OBJECT);
		Long friendId = friendDeleteReq.getFriendId();
		ThrowUtil.tif(ObjectUtil.isNull(friendId) || friendId <= BaseConstant.ZERO, "请选择要删除的好友");
		friendService.deleteFriend(StpUtil.getLoginIdAsLong(), friendId);
		return Result.success();
	}
}
