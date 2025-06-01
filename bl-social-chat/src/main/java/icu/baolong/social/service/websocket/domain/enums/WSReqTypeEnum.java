package icu.baolong.social.service.websocket.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * WebSocket 请求类型
 *
 * @author Silas Yan 2025-05-20 21:47
 */
@Getter
@AllArgsConstructor
public enum WSReqTypeEnum {

	LOGIN_CAPTCHA(0, "请求图形验证码"),
	LOGIN_WX_QR_CODE(1, "微信扫码登录"),
	HEARTBEAT(2, "心跳包"),
	AUTHORIZE(3, "登录认证"),
	;

	private final Integer key;

	private final String label;

	private static final Map<Integer, WSReqTypeEnum> CACHE;

	static {
		CACHE = Arrays.stream(WSReqTypeEnum.values()).collect(Collectors.toMap(WSReqTypeEnum::getKey, Function.identity()));
	}

	public static WSReqTypeEnum of(Integer type) {
		return CACHE.get(type);
	}
}
