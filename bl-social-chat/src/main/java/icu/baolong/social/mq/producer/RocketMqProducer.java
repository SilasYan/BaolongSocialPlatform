package icu.baolong.social.mq.producer;

import com.alibaba.fastjson.JSON;
import icu.baolong.social.function.transaction.SecureInvoke;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * RocketMQ消息生产者
 *
 * @author Silas Yan 2025-06-04 20:48
 */
@Slf4j
@Component
@Import(RocketMQAutoConfiguration.class)
public class RocketMqProducer {

	@Resource
	private RocketMQTemplate rocketMQTemplate;

	/**
	 * 发送消息
	 *
	 * @param topic 主题
	 * @param body  消息
	 */
	public void sendMsg(String topic, Object body) {
		String jsonBody = JSON.toJSONString(body);
		log.info("发送消息: {}", jsonBody);
		rocketMQTemplate.convertAndSend(topic, jsonBody);
	}

	/**
	 * 发送可靠消息
	 *
	 * @param topic 主题
	 * @param body  消息
	 * @param key   键
	 */
	@SecureInvoke
	public void sendSecureMsg(String topic, Object body, Object key) {
		Message<Object> build = MessageBuilder
				.withPayload(body)
				.setHeader("KEYS", key)
				.build();
		log.info("发送可靠消息: {}", build);
		rocketMQTemplate.send(topic, build);
	}
}
