package icu.baolong.social.service.websocket.adapter;

import icu.baolong.social.service.websocket.domain.response.BlackUserResp;
import icu.baolong.social.repository.user.entity.User;
import icu.baolong.social.service.websocket.domain.response.LoginAuthorizeResp;
import icu.baolong.social.service.websocket.domain.response.LoginSuccessResp;
import icu.baolong.social.service.websocket.domain.response.LoginQRCodeResp;
import icu.baolong.social.service.websocket.domain.base.WSResp;
import icu.baolong.social.service.websocket.domain.enums.WSRespTypeEnum;
import icu.baolong.social.service.websocket.domain.response.TokenInvalidateResp;

/**
 * WebSocket适配器
 *
 * @author Silas Yan 2025-05-24 23:13
 */
public class WSAdapter {

	/**
	 * 构建登录二维码响应
	 *
	 * @param qrCodeUrl 二维码地址
	 * @return 登录二维码响应
	 */
	public static WSResp<LoginQRCodeResp> buildLoginQrCodeResp(String qrCodeUrl) {
		return WSResp.<LoginQRCodeResp>builder()
				.type(WSRespTypeEnum.LOGIN_QR_CODE_URL.getKey())
				.data(LoginQRCodeResp.builder().qrCodeUrl(qrCodeUrl).build())
				.build();
	}

	/**
	 * 构建登录授权响应
	 *
	 * @return 登录授权响应
	 */
	public static WSResp<LoginAuthorizeResp> buildLoginAuthorizeResp() {
		return WSResp.<LoginAuthorizeResp>builder()
				.type(WSRespTypeEnum.LOGIN_AUTHORIZE.getKey())
				.build();
	}

	/**
	 * 构建登录成功响应
	 *
	 * @param token Token
	 * @param user  用户对象
	 * @return 登录成功响应
	 */
	public static WSResp<LoginSuccessResp> buildLoginSuccessResp(String token, User user) {
		return WSResp.<LoginSuccessResp>builder()
				.type(WSRespTypeEnum.LOGIN_SUCCESS.getKey())
				.data(LoginSuccessResp.builder()
						.token(token)
						.userId(user.getUserId())
						.userAvatar(user.getUserAvatar())
						.userName(user.getUserName())
						.build())
				.build();
	}

	/**
	 * 构建Token失效响应
	 *
	 * @return Token失效响应
	 */
	public static WSResp<TokenInvalidateResp> buildTokenInvalidateResp() {
		return WSResp.<TokenInvalidateResp>builder()
				.type(WSRespTypeEnum.TOKEN_INVALIDATE.getKey())
				.build();
	}

	/**
	 * 构建拉黑用户响应
	 *
	 * @return 拉黑用户响应
	 */
	public static WSResp<BlackUserResp> buildBlackUserResp(Long userId) {
		return WSResp.<BlackUserResp>builder()
				.type(WSRespTypeEnum.BLACK_USER.getKey())
				.data(BlackUserResp.builder().userId(userId).build())
				.build();
	}
}
