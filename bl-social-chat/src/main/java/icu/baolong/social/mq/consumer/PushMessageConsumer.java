package icu.baolong.social.mq.consumer;

import cn.hutool.json.JSONUtil;
import icu.baolong.social.base.constants.MqConstant;
import icu.baolong.social.mq.domain.PushDTO;
import icu.baolong.social.mq.domain.PushTypeEnum;
import icu.baolong.social.service.websocket.service.WebSocketService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 推送消息消费者
 *
 * @author Silas Yan 2025-06-05 21:41
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = MqConstant.GROUP_PUSH_MESSAGE, topic = MqConstant.TOPIC_PUSH_MESSAGE, messageModel = MessageModel.BROADCASTING)
public class PushMessageConsumer implements RocketMQListener<String> {

	@Resource
	private WebSocketService webSocketService;

	@Override
	public void onMessage(String message) {
		log.info("[消费者-推送消息]消息：{}", message);
		PushDTO pushDTO = JSONUtil.toBean(message, PushDTO.class);
		switch (PushTypeEnum.of(pushDTO.getPushType())) {
			case USER:
				for (Long userId : pushDTO.getUserIdList()) {
					log.info("[消费者-推送消息]推送消息给用户：");
					webSocketService.pushToUser(userId, pushDTO.getWsResp());
				}
				break;
			case ALL:
				log.info("[消费者-推送消息]推送消息给全员");
				webSocketService.pushToAllOnline(pushDTO.getWsResp());
				break;
		}
	}
}
