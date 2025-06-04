package icu.baolong.social.module.message.handler;

import icu.baolong.social.module.message.enums.MessageTypeEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息处理器工厂
 *
 * @author Silas Yan 2025-06-03 21:17
 */
public class MessageHandlerFactory {

	private static final Map<Integer, AbstractMessageHandler<?>> MSG_MAP = new ConcurrentHashMap<>(16);

	/**
	 * 注册消息处理器
	 *
	 * @param messageTypeEnum 消息类型枚举
	 * @param msgHandler      消息处理器
	 */
	public static void register(MessageTypeEnum messageTypeEnum, AbstractMessageHandler<?> msgHandler) {
		MSG_MAP.put(messageTypeEnum.getKey(), msgHandler);
	}

	/**
	 * 获取消息处理器
	 *
	 * @param messageTypeEnum 消息类型枚举
	 * @return 消息处理器
	 */
	public static AbstractMessageHandler<?> getHandler(MessageTypeEnum messageTypeEnum) {
		return MSG_MAP.get(messageTypeEnum.getKey());
	}
}
