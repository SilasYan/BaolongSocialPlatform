package icu.baolong.social.service.websocket.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import icu.baolong.social.events.UserOfflineEventPublisher;
import icu.baolong.social.events.UserOnlineEventPublisher;
import icu.baolong.social.service.websocket.domain.base.WSResp;
import icu.baolong.social.service.websocket.utils.NettyUtils;
import icu.baolong.social.module.user.service.UserService;
import icu.baolong.social.repository.user.entity.IpInfo;
import icu.baolong.social.repository.user.entity.User;
import icu.baolong.social.service.websocket.adapter.WSAdapter;
import icu.baolong.social.service.websocket.domain.ConnectEntity;
import icu.baolong.social.service.websocket.utils.PushUtils;
import icu.baolong.social.service.weixin.service.WeiXinService;
import io.netty.channel.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * WebSocket服务
 *
 * @author Silas Yan 2025-05-24 22:31
 */
@Slf4j
@Service
public class WebSocketService {

	@Resource
	private WeiXinService weiXinInteractiveService;
	@Resource
	private UserService userService;
	@Resource
	private ApplicationEventPublisher eventPublisher;
	@Resource
	private ThreadPoolTaskExecutor pushExecutor;

	/**
	 * 所有连接的channel, 包括用户的连接信息
	 */
	private static final ConcurrentHashMap<Channel, ConnectEntity> CONNECT_INFO_MAP = new ConcurrentHashMap<>();

	/**
	 * 所有请求的 qrSceneId 和 channel 的关系
	 */
	private static final Cache<Integer, Channel> SCENE_CHANNEL_MAP = Caffeine.newBuilder()
			.expireAfterWrite(Duration.ofMinutes(30)).maximumSize(1000L).build();

	/**
	 * 所有在线的用户
	 */
	private static final ConcurrentHashMap<Long, CopyOnWriteArrayList<Channel>> ONLINE_USER_MAP = new ConcurrentHashMap<>();

	/**
	 * 处理上线
	 *
	 * @param channel 管道
	 */
	public void handleOnline(Channel channel) {
		// 保存当前 channel
		CONNECT_INFO_MAP.put(channel, new ConnectEntity());
	}

	/**
	 * 处理下线
	 *
	 * @param channel 管道
	 */
	public void handleOffline(Channel channel) {
		// 移除 当前 channel
		CONNECT_INFO_MAP.remove(channel);
		// 移除 当前 用户ID
		ConnectEntity connectEntity = CONNECT_INFO_MAP.get(channel);
		if (connectEntity != null && ObjectUtil.isNotNull(connectEntity.getUserId())) {
			CopyOnWriteArrayList<Channel> channels = ONLINE_USER_MAP.get(connectEntity.getUserId());
			if (CollUtil.isNotEmpty(channels)) {
				channels.remove(channel);
			}
			// 发布下线事件
			publishUserOfflineEvent(connectEntity.getUserId());
		}
	}

	/**
	 * 处理微信扫码登录
	 *
	 * @param channel 管道
	 */
	public void handleLoginWxQrCode(Channel channel) {
		// 获取sceneId
		Integer qrSceneId = weiXinInteractiveService.getQrSceneId();
		// 获取临时二维码
		String qrCodeUrl = weiXinInteractiveService.getWxTempQrCode(qrSceneId);
		// 保存sceneId和channel的关系
		SCENE_CHANNEL_MAP.put(qrSceneId, channel);
		// 发送通知给前端
		PushUtils.pushMessage(channel, WSAdapter.buildLoginQrCodeResp(qrCodeUrl));
	}

	/**
	 * 处理微信扫码授权
	 *
	 * @param qrSceneId qrSceneId
	 */
	public void handleAuthorizing(Integer qrSceneId) {
		// 在通过 qrSceneId 获取 channel
		Channel channel = SCENE_CHANNEL_MAP.getIfPresent(qrSceneId);
		if (channel == null) {
			log.error("[WS]扫码授权, 但未找到对应的 channel, qrSceneId: {}", qrSceneId);
			return;
		}
		// 发送通知给前端
		PushUtils.pushMessage(channel, WSAdapter.buildLoginAuthorizeResp());
	}

	/**
	 * 处理微信扫码授权成功
	 *
	 * @param qrSceneId qrSceneId
	 * @param user      用户对象
	 * @param token     Token
	 */
	public void handleAuthorizeSuccess(Integer qrSceneId, User user, String token) {
		// 在通过 qrSceneId 获取 channel
		Channel channel = SCENE_CHANNEL_MAP.getIfPresent(qrSceneId);
		if (channel == null) {
			log.error("[WS]扫码授权成功, 但未找到对应的 channel, qrSceneId: {}", qrSceneId);
			return;
		}
		// 移除
		SCENE_CHANNEL_MAP.invalidate(qrSceneId);
		// 发送通知给前端
		PushUtils.pushMessage(channel, WSAdapter.buildLoginSuccessResp(token, user));
		// 发布事件
		publishUserOnlineEvent(channel, user);
	}

	/**
	 * 处理主动握手授权
	 *
	 * @param channel 管道
	 * @param token   Token
	 */
	public void handelHandshakeAuthorize(Channel channel, String token) {
		Object o = StpUtil.getLoginIdByToken(token);
		if (ObjectUtil.isNull(o)) {
			PushUtils.pushMessage(channel, WSAdapter.buildTokenInvalidateResp());
		} else {
			Long userId = Long.parseLong(o.toString());
			ConnectEntity connectEntity = CONNECT_INFO_MAP.get(channel);
			connectEntity.setUserId(userId);
			User user = userService.getUserByUserId(userId);
			// 存储当前用户ID
			ONLINE_USER_MAP.putIfAbsent(userId, new CopyOnWriteArrayList<>());
			ONLINE_USER_MAP.get(userId).add(channel);
			// 发送消息给前端
			PushUtils.pushMessage(channel, WSAdapter.buildLoginSuccessResp(token, user));
			// 发布事件
			publishUserOnlineEvent(channel, user);
		}
	}

	/**
	 * 发布用户上线事件
	 *
	 * @param channel 管道
	 * @param user    用户对象
	 */
	private void publishUserOnlineEvent(Channel channel, User user) {
		user.setLastLoginTime(new Date());
		String ip = NettyUtils.getAttr(channel, NettyUtils.IP);
		IpInfo ipInfo = IpInfo.builder().lastLoginIp(ip).build();
		if (StrUtil.isBlank(user.getIpInfo().getRegisterIp())) {
			ipInfo.setRegisterIp(ip);
		}
		user.setIpInfo(ipInfo);
		eventPublisher.publishEvent(new UserOnlineEventPublisher(this, user));
	}

	/**
	 * 发布用户下线事件
	 *
	 * @param userId 用户ID
	 */
	private void publishUserOfflineEvent(Long userId) {
		User user = new User();
		user.setUserId(userId);
		user.setLastOnlineTime(new Date());
		eventPublisher.publishEvent(new UserOfflineEventPublisher(this, user));
	}

	/**
	 * 推送消息给某个用户
	 *
	 * @param userId 用户ID
	 * @param wsResp 消息对象
	 */
	public void pushToUser(Long userId, WSResp<?> wsResp) {
		CopyOnWriteArrayList<Channel> channels = ONLINE_USER_MAP.get(userId);
		if (CollUtil.isEmpty(channels)) {
			log.info("[WS]用户[{}]不在线", userId);
			return;
		}
		channels.forEach(channel -> {
			pushExecutor.execute(() -> {
				PushUtils.pushMessage(channel, wsResp);
			});
		});
	}

	/**
	 * 推送消息给所有在线用户
	 *
	 * @param wsResp 消息对象
	 */
	public void pushToAllOnline(WSResp<?> wsResp) {
		CONNECT_INFO_MAP.forEach((channel, connectEntity) -> {
			pushExecutor.execute(() -> {
				PushUtils.pushMessage(channel, wsResp);
			});
		});
	}
}
