package icu.baolong.social.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 消息发送事件发布者
 *
 * @author Silas Yan 2025-06-04 20:52
 */
@Getter
public class MessageSendEventPublisher extends ApplicationEvent {
	private final Long messageId;

	public MessageSendEventPublisher(Object source, Long messageId) {
		super(source);
		this.messageId = messageId;
	}
}
