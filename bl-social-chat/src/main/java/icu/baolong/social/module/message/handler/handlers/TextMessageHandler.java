package icu.baolong.social.module.message.handler.handlers;

import icu.baolong.social.module.message.domain.response.TextMessageResp;
import icu.baolong.social.module.message.enums.MessageTypeEnum;
import icu.baolong.social.repository.message.entity.extra.TextMessageExtra;
import icu.baolong.social.module.message.domain.response.MessageResp;
import icu.baolong.social.module.message.handler.AbstractMessageHandler;
import icu.baolong.social.repository.message.entity.Message;
import org.springframework.stereotype.Component;

/**
 * 文本消息处理器
 *
 * @author Silas Yan 2025-06-03 21:33
 */
@Component
public class TextMessageHandler extends AbstractMessageHandler<TextMessageExtra> {
	/**
	 * 消息类型枚举
	 *
	 * @return 消息类型枚举
	 */
	@Override
	protected MessageTypeEnum messageTypeEnum() {
		return MessageTypeEnum.TEXT;
	}

	/**
	 * 校验消息
	 *
	 * @param userId 登录用户ID
	 * @param body   消息体
	 */
	@Override
	protected void checkMessage(Long userId, TextMessageExtra body) {

	}

	/**
	 * 填充消息
	 *
	 * @param message 消息对象
	 * @param body    消息体
	 */
	@Override
	protected void fillMessage(Message message, TextMessageExtra body) {
		message.setContent(body.getContent());
	}

	/**
	 * 构建消息响应
	 *
	 * @param userId  登录用户ID
	 * @param message 消息对象
	 * @return 消息响应
	 */
	@Override
	public MessageResp buildMessageResp(Long userId, Message message) {
		return MessageResp.builder()
				.userId(userId)
				.senderInfo(MessageResp.SenderInfo.builder()
						.senderId(message.getSenderId())
						.build())
				.messageInfo(MessageResp.MessageInfo.builder()
						.messageId(message.getMessageId())
						.messageType(message.getMessageType())
						.roomId(message.getRoomId())
						.sendTime(message.getCreateTime())
						.body(TextMessageResp.builder()
								.content(message.getContent())
								.build())
						.build())
				.interactionInfo(MessageResp.InteractionInfo.builder()
						.build())
				.build();
	}
}
