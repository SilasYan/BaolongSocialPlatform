package icu.baolong.social.module.message.service.impl;

import cn.hutool.core.collection.CollUtil;
import icu.baolong.social.common.exception.ThrowUtil;
import icu.baolong.social.events.MessageSendEventPublisher;
import icu.baolong.social.module.message.dao.MessageDao;
import icu.baolong.social.module.message.domain.request.MessageReq;
import icu.baolong.social.module.message.domain.response.MessageResp;
import icu.baolong.social.module.message.enums.MessageTypeEnum;
import icu.baolong.social.module.message.handler.AbstractMessageHandler;
import icu.baolong.social.module.message.handler.MessageHandlerFactory;
import icu.baolong.social.module.message.service.MessageService;
import icu.baolong.social.module.room.service.RoomService;
import icu.baolong.social.repository.message.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 消息表 (message) - 服务实现
 *
 * @author Baolong 2025-05-30 23:54:59
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final MessageDao messageDao;
	private final RoomService roomService;
	private final ApplicationEventPublisher eventPublisher;

	/**
	 * 发送消息
	 *
	 * @param userId     登录用户ID
	 * @param messageReq 消息请求
	 * @return 消息响应
	 */
	@Override
	public MessageResp sendMessage(Long userId, MessageReq messageReq) {
		// 房间校验
		roomService.checkRoom(userId, messageReq.getRoomId());
		MessageTypeEnum messageTypeEnum = MessageTypeEnum.of(messageReq.getMessageType());
		ThrowUtil.tif(messageTypeEnum == null, "消息类型错误");
		// 获取执行消息的处理器
		AbstractMessageHandler<?> messageHandler = MessageHandlerFactory.getHandler(messageTypeEnum);
		Long messageId = messageHandler.executeSend(userId, messageReq);
		// 发布消息发送事件
		eventPublisher.publishEvent(new MessageSendEventPublisher(this, messageId));
		return this.getMessage(userId, messageId);
	}

	/**
	 * 获取消息
	 *
	 * @param userId    登录用户ID
	 * @param messageId 消息ID
	 * @return 消息响应
	 */
	@Override
	public MessageResp getMessage(Long userId, Long messageId) {
		Message message = messageDao.getById(messageId);
		return CollUtil.getFirst(this.getMessages(userId, Collections.singletonList(message)));
	}

	/**
	 * 获取消息列表
	 *
	 * @param userId   登录用户ID
	 * @param messages 消息列表
	 * @return 消息响应列表
	 */
	@Override
	public List<MessageResp> getMessages(Long userId, List<Message> messages) {
		if (CollUtil.isEmpty(messages)) {
			return List.of();
		}
		return messages.stream().map(message -> {
			AbstractMessageHandler<?> messageHandler = MessageHandlerFactory.getHandler(MessageTypeEnum.of(message.getMessageType()));
			return messageHandler.buildMessageResp(userId, message);
		}).toList();
	}
}
