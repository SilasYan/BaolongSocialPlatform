package icu.baolong.social.module.message.service.impl;

import icu.baolong.social.common.exception.ThrowUtil;
import icu.baolong.social.module.message.dao.MessageDao;
import icu.baolong.social.module.message.domain.request.MessageReq;
import icu.baolong.social.module.message.domain.response.MessageResp;
import icu.baolong.social.module.message.enums.MessageTypeEnum;
import icu.baolong.social.module.message.handler.AbstractMessageHandler;
import icu.baolong.social.module.message.handler.MessageHandlerFactory;
import icu.baolong.social.module.message.service.MessageService;
import icu.baolong.social.module.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
		MessageTypeEnum messageTypeEnum = MessageTypeEnum.of(messageReq.getMsgType());
		ThrowUtil.tif(messageTypeEnum == null, "消息类型错误");
		// 获取执行消息的处理器
		AbstractMessageHandler<?> messageHandler = MessageHandlerFactory.getHandler(messageTypeEnum);
		Long messageId = messageHandler.execute(userId, messageReq);
		return null;
	}
}
