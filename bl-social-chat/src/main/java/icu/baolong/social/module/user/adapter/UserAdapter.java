package icu.baolong.social.module.user.adapter;

import icu.baolong.social.module.user.domain.enums.UserSexEnum;
import icu.baolong.social.repository.user.entity.User;

/**
 * 用户适配器
 *
 * @author Silas Yan 2025-05-24 19:17
 */
public class UserAdapter {

	/**
	 * 生成默认用户
	 *
	 * @param userEmail    用户邮箱
	 * @param userPassword 用户密码（已经加密的密码）
	 * @return 用户对象
	 */
	public static User buildDefaultUser(String userEmail, String userPassword) {
		User user = new User();
		user.setUserEmail(userEmail).setUserPassword(userPassword);
		String account = "user." + user.getUserEmail().split("@")[0];
		user.setUserAccount(account).setUserName(account)
				.setUserSex(UserSexEnum.OTHER.getKey()).setUserProfile("用户暂未填写个人简介~");
		return user;
	}

	/**
	 * 生成微信用户
	 *
	 * @param wxOpenId     微信公众号OpenId
	 * @param userPassword 用户密码（已经加密的密码）
	 * @return 用户对象
	 */
	public static User buildUserByWxOpenId(String wxOpenId, String userPassword) {
		User user = new User();
		user.setWxOpenId(wxOpenId).setUserPassword(userPassword)
				.setUserSex(UserSexEnum.OTHER.getKey()).setUserProfile("用户暂未填写个人简介~");
		return user;
	}

}
