package icu.baolong.social.module.message.adapter;

import icu.baolong.social.module.message.domain.request.MessageReq;
import icu.baolong.social.repository.message.entity.Message;

/**
 * 消息适配器
 *
 * @author Silas Yan 2025-06-03 21:38
 */
public class MessageAdapter {

	public static Message buildMessage(Long senderId, MessageReq messageReq) {
		return new Message()
				.setSenderId(senderId)
				.setRoomId(messageReq.getRoomId())
				.setMessageType(messageReq.getMessageType());
	}
}
