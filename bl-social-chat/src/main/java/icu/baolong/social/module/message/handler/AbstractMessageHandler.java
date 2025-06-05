package icu.baolong.social.module.message.handler;

import cn.hutool.core.bean.BeanUtil;
import icu.baolong.social.common.exception.ThrowUtil;
import icu.baolong.social.module.message.adapter.MessageAdapter;
import icu.baolong.social.module.message.dao.MessageDao;
import icu.baolong.social.module.message.domain.request.MessageReq;
import icu.baolong.social.module.message.enums.MessageTypeEnum;
import icu.baolong.social.module.message.domain.response.MessageResp;
import icu.baolong.social.repository.message.entity.Message;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;

/**
 * 消息处理器（抽象基类）
 *
 * @author Silas Yan 2025-06-03 21:13
 */
@SuppressWarnings(value = {"unchecked"})
public abstract class AbstractMessageHandler<T> {

	@Resource
	private MessageDao messageDao;

	private Class<T> clazz;

	@PostConstruct
	protected void init() {
		// 获取泛型
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		// 注册消息处理器
		MessageHandlerFactory.register(messageTypeEnum(), this);
	}

	/**
	 * 消息类型枚举
	 *
	 * @return 消息类型枚举
	 */
	protected abstract MessageTypeEnum messageTypeEnum();

	/**
	 * 执行消息的方法
	 *
	 * @param senderId 发送者ID
	 * @param req      消息请求
	 * @return 消息ID
	 */
	@Transactional(rollbackFor = Exception.class)
	public Long executeSend(Long senderId, MessageReq req) {
		T body = BeanUtil.toBean(req.getBody(), clazz);
		this.checkMessage(senderId, body);
		Message message = MessageAdapter.buildMessage(senderId, req);
		this.fillMessage(message, body);
		boolean result = messageDao.save(message);
		ThrowUtil.tif(!result, "消息发送失败!");
		return message.getMessageId();
	}

	/**
	 * 校验消息
	 *
	 * @param userId 登录用户ID
	 * @param body   消息体
	 */
	protected abstract void checkMessage(Long userId, T body);

	/**
	 * 填充消息
	 *
	 * @param message 消息对象
	 * @param body    消息体
	 */
	protected abstract void fillMessage(Message message, T body);

	/**
	 * 构建消息响应
	 *
	 * @param userId  登录用户ID
	 * @param message 消息对象
	 * @return 消息响应
	 */
	public abstract MessageResp buildMessageResp(Long userId, Message message);
}
