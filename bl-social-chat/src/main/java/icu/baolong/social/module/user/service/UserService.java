package icu.baolong.social.module.user.service;

import icu.baolong.social.module.user.domain.request.UserLoginReq;
import icu.baolong.social.module.user.domain.request.UserRegisterReq;
import icu.baolong.social.module.user.domain.response.EmailCodeResp;
import icu.baolong.social.module.user.domain.response.GraphicCodeResp;
import icu.baolong.social.module.user.domain.response.UserBadgeResp;
import icu.baolong.social.module.user.domain.response.UserInfoResp;
import icu.baolong.social.repository.user.entity.User;

import java.util.List;

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
	 * 用户登录（扫码登录）
	 *
	 * @param wxOpenId 微信OpenId
	 * @return Token
	 */
	String userLoginByScanQrCode(String wxOpenId);

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

	/**
	 * 根据用户ID获取用户信息
	 *
	 * @param userId 用户ID
	 * @return 用户对象
	 */
	User getUserByUserId(Long userId);

	/**
	 * 根据微信OpenId获取用户信息
	 *
	 * @param openId 微信OpenId
	 * @return 用户对象
	 */
	User getUserByWxOpenId(String openId);

	/**
	 * 根据用户名称获取用户信息
	 *
	 * @param userName 用户名称
	 * @return 用户对象
	 */
	User getUserByUserName(String userName);

	/**
	 * 根据微信OpenId注册用户
	 *
	 * @param openId 微信OpenId
	 */
	void userRegisterByWxOpenId(String openId);

	/**
	 * 用户微信授权更新信息
	 *
	 * @param wxOpenId   微信OpenId
	 * @param userName   微信用户昵称
	 * @param userAvatar 微信头像
	 * @param userSex    微信性别
	 * @return 用户对象
	 */
	User userUpdateByWxAuthorize(String wxOpenId, String userName, String userAvatar, Integer userSex);

	/**
	 * 修改用户名称
	 *
	 * @param userName 用户名称
	 */
	void modifyUserName(String userName);

	/**
	 * 获取徽章列表
	 *
	 * @return 徽章列表
	 */
	List<UserBadgeResp> getBadgeList();

	/**
	 * 修改用户徽章
	 *
	 * @param badgeId 徽章ID
	 */
	void modifyUserBadge(Long badgeId);

	/**
	 * 拉黑用户
	 *
	 * @param userId 用户ID
	 */
	void blackUser(Long userId);
}
