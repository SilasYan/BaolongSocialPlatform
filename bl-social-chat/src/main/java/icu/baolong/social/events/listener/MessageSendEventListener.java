package icu.baolong.social.events.listener;

import icu.baolong.social.base.constants.MqConstant;
import icu.baolong.social.events.MessageSendEventPublisher;
import icu.baolong.social.mq.producer.RocketMqProducer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 消息发送事件监听者
 *
 * @author Silas Yan 2025-06-04 22:09
 */
@Component
public class MessageSendEventListener {

	@Resource
	private RocketMqProducer rocketMqProducer;

	/**
	 * 监听发送消息, 发送给MQ
	 *
	 * @param event 事件对象
	 */
	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, classes = MessageSendEventPublisher.class, fallbackExecution = true)
	public void notifyUser(MessageSendEventPublisher event) {
		rocketMqProducer.sendSecureMsg(MqConstant.TOPIC_SEND_MESSAGE, event.getMessageId(), event.getMessageId());
	}
}
