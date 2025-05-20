package icu.baolong.social.enums;

import icu.baolong.social.entity.response.CaptchaResp;
import icu.baolong.social.entity.response.LoginSuccessResp;
import icu.baolong.social.entity.response.MessageResp;
import icu.baolong.social.entity.response.OnlineOfflineNotifyResp;
import icu.baolong.social.entity.response.QRCodeResp;
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
public enum WebSocketRespTypeEnum {

	LOGIN_CAPTCHA(0, "登录图形验证码", CaptchaResp.class),
	LOGIN_QR_URL(1, "登录二维码地址", QRCodeResp.class),
	LOGIN_AUTHORIZE(2, "登录授权", null),
	LOGIN_SUCCESS(3, "登录成功", LoginSuccessResp.class),
	MESSAGE(4, "消息", MessageResp.class),
	ONLINE_OFFLINE_NOTIFY(5, "上下线通知", OnlineOfflineNotifyResp.class),
	INVALIDATE_TOKEN(6, "Token失效", null),
	;

	private final Integer key;

	private final String label;
	private final Class clazz;

	private static final Map<Integer, WebSocketRespTypeEnum> CACHE;

	static {
		CACHE = Arrays.stream(WebSocketRespTypeEnum.values()).collect(Collectors.toMap(WebSocketRespTypeEnum::getKey, Function.identity()));
	}

	public static WebSocketRespTypeEnum of(Integer type) {
		return CACHE.get(type);
	}
}
