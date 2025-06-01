package icu.baolong.social.common.constants;

/**
 * KEY常量
 *
 * @author Silas Yan 2025-04-06:01:04
 */
public interface KeyConstant {

	/**
	 * 邮箱验证码 KEY 前缀
	 */
	String PREFIX_EMAIL_CODE = "EMAIL_CODE:%s";

	/**
	 * 图形验证码 KEY 前缀
	 */
	String PREFIX_GRAPHIC_CODE = "GRAPHIC_CODE:%s";

	/**
	 * 用户信息 KEY 前缀
	 */
	String PREFIX_USER_INFO = "USER_INFO:%s";

	/**
	 * 微信场景值 KEY
	 */
	String KEY_WX_SCENE_ID = "wx_scene_id";

	/**
	 * 微信 openId 和场景值 KEY
	 */
	String PREFIX_WX_OPENID_SCENE = "wx_openid_scene:%s";

	/**
	 * 用户权限 KEY 前缀
	 */
	String PREFIX_USER_AUTH = "user_auth:%s";
}
