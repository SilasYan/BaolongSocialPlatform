package icu.baolong.social.module.message.service;

import icu.baolong.social.module.message.domain.request.MessageReq;
import icu.baolong.social.module.message.domain.response.MessageResp;
import icu.baolong.social.repository.message.entity.Message;

import java.util.List;

/**
 * 消息表 (message) - 服务接口
 *
 * @author Baolong 2025-05-30 23:54:59
 */
public interface MessageService {

	/**
	 * 发送消息
	 *
	 * @param userId     登录用户ID
	 * @param messageReq 消息请求
	 * @return 消息响应
	 */
	MessageResp sendMessage(Long userId, MessageReq messageReq);

	/**
	 * 获取消息
	 *
	 * @param userId    登录用户ID
	 * @param messageId 消息ID
	 * @return 消息响应
	 */
	MessageResp getMessage(Long userId, Long messageId);

	/**
	 * 获取消息列表
	 *
	 * @param userId   登录用户ID
	 * @param messages 消息列表
	 * @return 消息响应列表
	 */
	List<MessageResp> getMessages(Long userId, List<Message> messages);
}
