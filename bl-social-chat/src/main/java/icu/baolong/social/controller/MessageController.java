package icu.baolong.social.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import icu.baolong.social.base.response.BaseResponse;
import icu.baolong.social.base.response.Result;
import icu.baolong.social.common.exception.ThrowUtil;
import icu.baolong.social.base.constants.TextConstant;
import icu.baolong.social.module.message.domain.request.MessageReq;
import icu.baolong.social.module.message.domain.response.MessageResp;
import icu.baolong.social.module.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息控制器
 *
 * @author Silas Yan 2025-06-03 20:30
 */
@Tag(name = "消息接口", description = "消息相关业务接口")
@RestController
@RequestMapping("/api/msg")
public class MessageController {
	@Resource
	private MessageService messageService;

	@Operation(summary = "发送消息", description = "发送消息")
	@PostMapping("/send")
	public BaseResponse<MessageResp> sendMessage(@RequestBody MessageReq messageReq) {
		ThrowUtil.nullIf(messageReq, TextConstant.ERROR_NULL_OBJECT);
		ThrowUtil.tif(ObjectUtil.isNull(messageReq.getRoomId()), "房间号不能为空");
		ThrowUtil.tif(ObjectUtil.isNull(messageReq.getMsgType()), "消息类型不能为空");
		return Result.success(messageService.sendMessage(StpUtil.getLoginIdAsLong(), messageReq));
	}

}
