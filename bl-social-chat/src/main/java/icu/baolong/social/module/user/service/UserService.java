package icu.baolong.social.module.user.service;

import icu.baolong.social.module.user.domain.request.UserLoginReq;
import icu.baolong.social.module.user.domain.request.UserRegisterReq;
import icu.baolong.social.module.user.domain.response.EmailCodeResp;
import icu.baolong.social.module.user.domain.response.GraphicCodeResp;
import icu.baolong.social.module.user.domain.response.UserInfoResp;

/**
 * 用户表 (user) - 服务接口
 *
 * @author Baolong 2025-05-21 21:01:07
 */
public interface UserService {

	/**
	 * 发送邮箱验证码
	 *
	 * @param userEmail 用户邮箱
	 * @return 邮箱验证码响应
	 */
	EmailCodeResp sendEmailCode(String userEmail);

	/**
	 * 用户注册
	 *
	 * @param userRegisterReq 用户注册请求
	 * @return 是否成功
	 */
	Boolean userRegister(UserRegisterReq userRegisterReq);

	/**
	 * 获取图形验证码
	 *
	 * @return 图形验证码响应
	 */
	GraphicCodeResp getGraphicCode();

	/**
	 * 用户登录
	 *
	 * @param userLoginReq 用户登录请求
	 * @return Token
	 */
	String userLogin(UserLoginReq userLoginReq);

	/**
	 * 用户登出
	 *
	 * @return 是否成功
	 */
	Boolean userLogout();

	/**
	 * 获取登录用户信息
	 *
	 * @return 用户信息响应
	 */
	UserInfoResp getUserInfoByLogin();
}
