package icu.baolong.social.websocket.utils;

import cn.hutool.json.JSONUtil;
import icu.baolong.social.websocket.domain.base.WSResp;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 推送工具类
 *
 * @author Silas Yan 2025-05-25 00:22
 */
public class PushUtils {

	private static final Logger log = LoggerFactory.getLogger(PushUtils.class);

	/**
	 * 推送消息给客户端
	 *
	 * @param channel channel
	 * @param wsResp  WebSocket响应
	 */
	public static void pushMessage(Channel channel, WSResp<?> wsResp) {
		TextWebSocketFrame frame = new TextWebSocketFrame(JSONUtil.toJsonStr(wsResp));
		log.info("[WS]推送消息给客户端: {}", JSONUtil.toJsonStr(wsResp));
		channel.writeAndFlush(frame);
	}
}
