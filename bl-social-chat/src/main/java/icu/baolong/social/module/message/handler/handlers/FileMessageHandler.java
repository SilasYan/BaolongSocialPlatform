package icu.baolong.social.module.message.handler.handlers;

import icu.baolong.social.module.message.enums.MessageTypeEnum;
import icu.baolong.social.repository.message.entity.extra.FileMessageExtra;
import icu.baolong.social.module.message.domain.response.MessageResp;
import icu.baolong.social.module.message.handler.AbstractMessageHandler;
import icu.baolong.social.repository.message.entity.Message;
import org.springframework.stereotype.Component;

/**
 * 文件消息处理器
 *
 * @author Silas Yan 2025-06-03 22:02
 */
@Component
public class FileMessageHandler extends AbstractMessageHandler<FileMessageExtra> {
	/**
	 * 消息类型枚举
	 *
	 * @return 消息类型枚举
	 */
	@Override
	protected MessageTypeEnum messageTypeEnum() {
		return MessageTypeEnum.FILE;
	}

	/**
	 * 校验消息
	 *
	 * @param userId 登录用户ID
	 * @param body   消息体
	 */
	@Override
	protected void checkMessage(Long userId, FileMessageExtra body) {

	}

	/**
	 * 填充消息
	 *
	 * @param message 消息对象
	 * @param body    消息体
	 */
	@Override
	protected void fillMessage(Message message, FileMessageExtra body) {

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
