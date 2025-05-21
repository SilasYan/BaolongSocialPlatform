package icu.baolong.social.user.controller;

import cn.hutool.core.util.StrUtil;
import icu.baolong.social.common.constants.TextConstant;
import icu.baolong.social.common.exception.ThrowUtil;
import icu.baolong.social.common.response.BaseResponse;
import icu.baolong.social.common.response.Result;
import icu.baolong.social.user.domain.request.EmailCodeReq;
import icu.baolong.social.user.domain.request.UserRegisterReq;
import icu.baolong.social.user.domain.response.EmailCodeResp;
import icu.baolong.social.user.domain.response.GraphicCodeResp;
import icu.baolong.social.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 *
 * @author Silas Yan 2025-05-21 22:10
 */
@Tag(name = "用户接口", description = "用户相关业务接口")
@RestController
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;

	@Operation(summary = "发送邮箱验证码", description = "发送邮箱验证码")
	@PostMapping("/emailCode")
	public BaseResponse<EmailCodeResp> sendEmailCode(@RequestBody EmailCodeReq emailCodeReq) {
		ThrowUtil.nullIf(emailCodeReq, TextConstant.ERROR_NULL_OBJECT);
		ThrowUtil.checkEmail(emailCodeReq.getUserEmail());
		return Result.success(userService.sendEmailCode(emailCodeReq.getUserEmail()));
	}

	@Operation(summary = "用户注册", description = "包含多种注册方式")
	@PostMapping("/register")
	public BaseResponse<Boolean> userRegister(@RequestBody UserRegisterReq userRegisterReq) {
		ThrowUtil.nullIf(userRegisterReq, TextConstant.ERROR_NULL_OBJECT);
		ThrowUtil.checkEmail(userRegisterReq.getUserEmail());
		String codeKey = userRegisterReq.getCodeKey();
		ThrowUtil.tif(StrUtil.isBlank(codeKey), TextConstant.ERROR_PARAMETER);
		String codeValue = userRegisterReq.getCodeValue();
		ThrowUtil.tif(StrUtil.isBlank(codeValue), "验证码不能为空");
		return Result.success("注册成功!", userService.userRegister(userRegisterReq));
	}

	@Operation(summary = "获取图形验证码", description = "用于登录场景")
	@GetMapping("/graphicCode")
	public BaseResponse<GraphicCodeResp> getGraphicCode() {
		return Result.success(userService.getGraphicCode());
	}
}
