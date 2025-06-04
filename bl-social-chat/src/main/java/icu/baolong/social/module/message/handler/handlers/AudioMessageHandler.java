package icu.baolong.social.module.message.handler.handlers;

import icu.baolong.social.module.message.enums.MessageTypeEnum;
import icu.baolong.social.repository.message.entity.extra.AudioMessageExtra;
import icu.baolong.social.module.message.domain.response.MessageResp;
import icu.baolong.social.module.message.handler.AbstractMessageHandler;
import icu.baolong.social.repository.message.entity.Message;
import org.springframework.stereotype.Component;

/**
 * 语音消息处理器
 *
 * @author Silas Yan 2025-06-03 21:59
 */
@Component
public class AudioMessageHandler extends AbstractMessageHandler<AudioMessageExtra> {
	/**
	 * 消息类型枚举
	 *
	 * @return 消息类型枚举
	 */
	@Override
	protected MessageTypeEnum messageTypeEnum() {
		return MessageTypeEnum.AUDIO;
	}

	/**
	 * 校验消息
	 *
	 * @param userId 用户ID
	 * @param body   消息体
	 */
	@Override
	protected void checkMessage(Long userId, AudioMessageExtra body) {

	}

	/**
	 * 填充消息
	 *
	 * @param message 消息对象
	 * @param body    消息体
	 */
	@Override
	protected void fillMessage(Message message, AudioMessageExtra body) {

	}

	/**
	 * 构建消息响应
	 *
	 * @param message 消息对象
	 * @return 消息响应
	 */
	@Override
	protected MessageResp buildMessageResp(Message message) {
		return null;
	}
}
