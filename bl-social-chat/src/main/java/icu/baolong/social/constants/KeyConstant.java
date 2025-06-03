package icu.baolong.social.constants;

/**
 * KEY常量
 *
 * @author Silas Yan 2025-04-06:01:04
 */
public interface KeyConstant {

	static final String PREFIX = "baolong_social:";

	/**
	 * 邮箱验证码 KEY 前缀
	 */
	String PREFIX_EMAIL_CODE = PREFIX + "EMAIL_CODE:%s";

	/**
	 * 图形验证码 KEY 前缀
	 */
	String PREFIX_GRAPHIC_CODE = PREFIX + "GRAPHIC_CODE:%s";

	/**
	 * 用户信息 KEY 前缀
	 */
	String PREFIX_USER_INFO = PREFIX + "USER_INFO:%s";

	/**
	 * 微信场景值 KEY
	 */
	String KEY_WX_SCENE_ID = PREFIX + "wx_scene_id";

	/**
	 * 微信 openId 和场景值 KEY
	 */
	String PREFIX_WX_OPENID_SCENE = PREFIX + "wx_openid_scene:%s";

	/**
	 * 用户权限 KEY 前缀
	 */
	String PREFIX_USER_AUTH = PREFIX + "user_auth:%s";
}
