package icu.baolong.social.mq.service;

import icu.baolong.social.base.constants.MqConstant;
import icu.baolong.social.mq.domain.PushDTO;
import icu.baolong.social.mq.domain.PushTypeEnum;
import icu.baolong.social.mq.producer.RocketMqProducer;
import icu.baolong.social.service.websocket.domain.base.WSResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * MQ推送服务
 *
 * @author Silas Yan 2025-06-05 21:10
 */
@Service
public class MqPushService {

	@Resource
	private RocketMqProducer rocketMqProducer;

	public void push(WSResp<?> wsResp) {
		rocketMqProducer.sendMsg(MqConstant.TOPIC_PUSH_MESSAGE, PushDTO.builder()
				.pushType(PushTypeEnum.ALL.getKey())
				.wsResp(wsResp)
				.build());
	}

	public void push(Long userId, WSResp<?> wsResp) {
		rocketMqProducer.sendMsg(MqConstant.TOPIC_PUSH_MESSAGE, PushDTO.builder()
				.pushType(PushTypeEnum.USER.getKey())
				.userIdList(Collections.singletonList(userId))
				.wsResp(wsResp)
				.build());
	}

	public void push(List<Long> userIdList, WSResp<?> wsResp) {
		rocketMqProducer.sendMsg(MqConstant.TOPIC_PUSH_MESSAGE, PushDTO.builder()
				.pushType(PushTypeEnum.USER.getKey())
				.userIdList(userIdList)
				.wsResp(wsResp)
				.build());
	}
}
