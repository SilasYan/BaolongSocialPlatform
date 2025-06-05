package icu.baolong.social.mq.consumer;

import icu.baolong.social.base.constants.MqConstant;
import icu.baolong.social.cache.RoomCache;
import icu.baolong.social.module.message.domain.response.MessageResp;
import icu.baolong.social.module.message.service.MessageService;
import icu.baolong.social.module.room.dao.RoomContactDao;
import icu.baolong.social.module.room.dao.RoomDao;
import icu.baolong.social.module.room.dao.RoomSingleDao;
import icu.baolong.social.module.room.enums.RoomTypeEnum;
import icu.baolong.social.module.room.enums.ShowTypeEnum;
import icu.baolong.social.mq.service.MqPushService;
import icu.baolong.social.repository.room.entity.Room;
import icu.baolong.social.repository.room.entity.RoomSingle;
import icu.baolong.social.service.websocket.adapter.WSAdapter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 发送消息消费者
 *
 * @author Silas Yan 2025-06-05 19:45
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = MqConstant.GROUP_SEND_MESSAGE, topic = MqConstant.TOPIC_SEND_MESSAGE)
public class SendMessageConsumer implements RocketMQListener<Long> {

	@Resource
	private MessageService messageService;
	@Resource
	private RoomDao roomDao;
	@Resource
	private RoomCache roomCache;
	@Resource
	private MqPushService mqPushService;
	@Resource
	private RoomSingleDao roomSingleDao;
	@Resource
	private RoomContactDao roomContactDao;

	@Override
	public void onMessage(Long messageId) {
		log.info("[消费者-发送消息]收到消息：{}", messageId);
		// 获取消息响应对象
		MessageResp messageResp = messageService.getMessage(null, messageId);
		// 更新房间中最后一条消息的信息
		Date sendTime = messageResp.getMessageInfo().getSendTime();
		Long roomId = messageResp.getMessageInfo().getRoomId();
		roomDao.updateRoomLastMessageInfo(roomId, messageId, sendTime);
		// 根据展示类型不同进行推送
		Room room = roomDao.getById(roomId);
		// 全员群, 推送给所有人
		if (ShowTypeEnum.ALL_STAFF.getKey().equals(room.getShowType())) {
			// 更新房间的排序
			roomCache.sortRoom(room.getRoomId(), sendTime);
			// 推送给所有人
			mqPushService.push(WSAdapter.buildMessageResp(messageResp));
		} else {
			// 非全员群, 根据房间类型进行推送
			List<Long> userIdList = new ArrayList<>();
			if (RoomTypeEnum.SINGLE_CHAT.getKey().equals(room.getRoomType())) {
				// 单聊, 推送给对方
				RoomSingle roomSingle = roomSingleDao.getRoomFriendByRoomId(roomId);
				userIdList = Arrays.asList(roomSingle.getUserId1(), roomSingle.getUserId2());
			} else {
				// 群聊, 推送给群组所有人
				userIdList = roomCache.getRoomGroupMemberList(roomId);
			}
			// 更新列表中所有人的会话信息
			roomContactDao.updateRoomContactLastMessageInfo(roomId, userIdList, messageId, sendTime);
			// 推送给列表中的所有人
			mqPushService.push(userIdList, WSAdapter.buildMessageResp(messageResp));
		}
	}
}
