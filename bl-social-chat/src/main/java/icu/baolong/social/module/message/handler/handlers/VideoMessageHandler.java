package icu.baolong.social.module.message.handler.handlers;

import icu.baolong.social.module.message.enums.MessageTypeEnum;
import icu.baolong.social.repository.message.entity.extra.VideoMessageExtra;
import icu.baolong.social.module.message.domain.response.MessageResp;
import icu.baolong.social.module.message.handler.AbstractMessageHandler;
import icu.baolong.social.repository.message.entity.Message;
import org.springframework.stereotype.Component;

/**
 * 视频消息处理器
 *
 * @author Silas Yan 2025-06-03 21:58
 */
@Component
public class VideoMessageHandler extends AbstractMessageHandler<VideoMessageExtra> {
	/**
	 * 消息类型枚举
	 *
	 * @return 消息类型枚举
	 */
	@Override
	protected MessageTypeEnum messageTypeEnum() {
		return MessageTypeEnum.VIDEO;
	}

	/**
	 * 校验消息
	 *
	 * @param userId 登录用户ID
	 * @param body   消息体
	 */
	@Override
	protected void checkMessage(Long userId, VideoMessageExtra body) {

	}

	/**
	 * 填充消息
	 *
	 * @param message 消息对象
	 * @param body    消息体
	 */
	@Override
	protected void fillMessage(Message message, VideoMessageExtra body) {

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
		return null;
	}
}
