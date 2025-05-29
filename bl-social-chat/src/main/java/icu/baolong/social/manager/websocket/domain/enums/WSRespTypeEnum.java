package icu.baolong.social.manager.websocket.domain.enums;

import icu.baolong.social.manager.websocket.domain.response.CaptchaResp;
import icu.baolong.social.manager.websocket.domain.response.LoginSuccessResp;
import icu.baolong.social.manager.websocket.domain.response.MessageResp;
import icu.baolong.social.manager.websocket.domain.response.OnlineOfflineNotifyResp;
import icu.baolong.social.manager.websocket.domain.response.LoginQRCodeResp;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * WebSocket 响应类型
 *
 * @author Silas Yan 2025-05-20 21:47
 */
@Getter
@AllArgsConstructor
public enum WSRespTypeEnum {

	LOGIN_CAPTCHA(0, "登录图形验证码", CaptchaResp.class),
	LOGIN_QR_CODE_URL(1, "登录二维码地址", LoginQRCodeResp.class),
	LOGIN_AUTHORIZE(2, "登录授权", null),
	LOGIN_SUCCESS(3, "登录成功", LoginSuccessResp.class),
	TOKEN_INVALIDATE(4, "Token失效", null),
	ONLINE_OFFLINE_NOTIFY(5, "上下线通知", OnlineOfflineNotifyResp.class),
	MESSAGE(6, "消息", MessageResp.class),
	BLACK_USER(7, "拉黑用户", MessageResp.class),
	;

	private final Integer key;

	private final String label;
	private final Class clazz;

	private static final Map<Integer, WSRespTypeEnum> CACHE;

	static {
		CACHE = Arrays.stream(WSRespTypeEnum.values()).collect(Collectors.toMap(WSRespTypeEnum::getKey, Function.identity()));
	}

	public static WSRespTypeEnum of(Integer key) {
		return CACHE.get(key);
	}
}
