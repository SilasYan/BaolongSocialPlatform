package icu.baolong.social.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import icu.baolong.social.common.entity.constants.BaseConstant;
import icu.baolong.social.common.exception.BusinessException;
import icu.baolong.social.common.function.limit.Limit;
import icu.baolong.social.common.function.limit.LimitType;
import icu.baolong.social.entity.constants.TextConstant;
import icu.baolong.social.common.exception.ThrowUtil;
import icu.baolong.social.common.response.BaseResponse;
import icu.baolong.social.common.response.Result;
import icu.baolong.social.module.user.domain.request.EmailCodeReq;
import icu.baolong.social.module.user.domain.request.UserLoginReq;
import icu.baolong.social.module.user.domain.request.UserModifyReq;
import icu.baolong.social.module.user.domain.request.UserRegisterReq;
import icu.baolong.social.module.user.domain.request.UserUpdateReq;
import icu.baolong.social.module.user.domain.response.EmailCodeResp;
import icu.baolong.social.module.user.domain.response.GraphicCodeResp;
import icu.baolong.social.module.user.domain.response.UserBadgeResp;
import icu.baolong.social.module.user.domain.response.UserInfoResp;
import icu.baolong.social.module.user.service.UserService;
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
 * 用户控制器
 *
 * @author Silas Yan 2025-05-21 22:10
 */
@Tag(name = "用户接口", description = "用户相关业务接口")
@RestController
@RequestMapping("/api/user")
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

	@Limit(limitType = LimitType.IP)
	@Operation(summary = "获取图形验证码", description = "用于登录场景")
	@GetMapping("/graphicCode")
	public BaseResponse<GraphicCodeResp> getGraphicCode() {
		return Result.success(userService.getGraphicCode());
	}

	@Operation(summary = "用户登录", description = "用户登录")
	@PostMapping("/login")
	public BaseResponse<String> userLogin(@RequestBody UserLoginReq userLoginReq) {
		ThrowUtil.nullIf(userLoginReq, TextConstant.ERROR_NULL_OBJECT);
		String account = userLoginReq.getAccount();
		ThrowUtil.tif(StrUtil.isBlank(account), "请输入账号或邮箱");
		if (account.length() < BaseConstant.FIVE) {
			throw new BusinessException("账号或邮箱长度不能小于5位");
		}
		String userPassword = userLoginReq.getUserPassword();
		ThrowUtil.tif(StrUtil.isBlank(userPassword), "请输入密码");
		if (userPassword.length() < BaseConstant.SIX) {
			throw new BusinessException("密码长度不能小于6位");
		}
		return Result.success("登录成功!", userService.userLogin(userLoginReq));
	}

	@Operation(summary = "用户登出", description = "用户登出")
	@GetMapping("/logout")
	public BaseResponse<Boolean> userLogout() {
		return Result.success("登出成功!", userService.userLogout());
	}

	@Operation(summary = "获取登录用户信息", description = "获取当前登录用户信息")
	@GetMapping("/info")
	public BaseResponse<UserInfoResp> getUserInfoByLogin() {
		return Result.success(userService.getUserInfoByLogin());
	}

	@Operation(summary = "修改用户名称", description = "修改用户名称")
	@PostMapping("/modifyUserName")
	public BaseResponse<Boolean> modifyUserName(@RequestBody UserModifyReq userModifyReq) {
		ThrowUtil.nullIf(userModifyReq, TextConstant.ERROR_NULL_OBJECT);
		String userName = userModifyReq.getUserName();
		ThrowUtil.tif(StrUtil.isBlank(userName), "请输入用户名");
		userService.modifyUserName(userName);
		return Result.success("修改成功!");
	}

	@Operation(summary = "获取徽章列表", description = "包含所有徽章以及拥有佩戴等信息")
	@GetMapping("/badgeList")
	public BaseResponse<List<UserBadgeResp>> getBadgeList() {
		return Result.success(userService.getBadgeList());
	}

	@Operation(summary = "修改用户徽章", description = "修改用户当前佩戴的徽章")
	@PostMapping("/modifyUserBadge")
	public BaseResponse<Boolean> modifyUserBadge(@RequestBody UserUpdateReq userUpdateReq) {
		ThrowUtil.nullIf(userUpdateReq, TextConstant.ERROR_NULL_OBJECT);
		Long badgeId = userUpdateReq.getBadgeId();
		ThrowUtil.tif(ObjectUtil.isNull(badgeId), "请选择徽章ID");
		userService.modifyUserBadge(badgeId);
		return Result.success("修改成功!");
	}
}
