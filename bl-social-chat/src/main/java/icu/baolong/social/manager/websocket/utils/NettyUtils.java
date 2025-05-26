package icu.baolong.social.manager.websocket.utils;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * Netty工具类
 *
 * @author Silas Yan 2025-05-25 01:36
 */
public class NettyUtils {

	public static AttributeKey<String> TOKEN = AttributeKey.valueOf("token");
	public static AttributeKey<String> IP = AttributeKey.valueOf("ip");

	public static <T> void setAttr(Channel channel, AttributeKey<T> attributeKey, T data) {
		Attribute<T> attr = channel.attr(attributeKey);
		attr.set(data);
	}

	public static <T> T getAttr(Channel channel, AttributeKey<T> ip) {
		return channel.attr(ip).get();
	}
}
