package icu.baolong.social.manager.weixin.service.impl;

import icu.baolong.social.common.utils.RedisUtil;
import icu.baolong.social.entity.constants.KeyConstant;
import icu.baolong.social.module.user.service.UserService;
import icu.baolong.social.manager.websocket.service.WebSocketService;
import icu.baolong.social.manager.weixin.builder.TextBuilder;
import icu.baolong.social.manager.weixin.service.WeiXinService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 微信相关接口 - 微信事件实现
 *
 * @author Silas Yan 2025-05-24 18:46
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeiXinEventService implements WeiXinService {

	private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

	@Value("${wechat.mp.callback}")
	private String callback;

	private final UserService userService;
	private final RedisUtil redisUtil;
	@Resource
	@Lazy
	private WebSocketService webSocketService;

	/**
	 * 扫码事件处理
	 *
	 * @param wxMpXmlMessage 微信消息对象
	 * @param wxMpService    微信SDK服务对象
	 * @return 处理后的消息
	 */
	@Override
	public WxMpXmlOutMessage scanHandle(WxMpXmlMessage wxMpXmlMessage, WxMpService wxMpService) {
		log.info("[微信]扫码事件处理, info: {}", wxMpXmlMessage);
		String event = wxMpXmlMessage.getEvent();
		log.info("[微信]eventType: {}", event);
		String openId = wxMpXmlMessage.getFromUser();
		log.info("[微信]openId: {}", openId);
		String eventKey = wxMpXmlMessage.getEventKey();
		log.info("[微信]eventKey: {}", eventKey);

		// // 查询是否存在, 存在直接登录即可
		// User user = userService.getUserByWxOpenId(openId);
		// if (user != null) {
		// 	log.error("[微信]用户已存在, openId: {}", openId);
		// 	return new TextBuilder().build("欢迎回来~", wxMpXmlMessage, wxMpService);
		// }

		if (WxConsts.EventType.SUBSCRIBE.equals(event)) {
			// 扫描关注事件: qrscene_711253
			userService.userRegisterByWxOpenId(openId);
		}

		Integer qrSceneId = Integer.parseInt(eventKey.replace("qrscene_", ""));
		// 存储当前 openId 及对应的 qrSceneId 到 redis 中, 后面授权需要根据 qrSceneId 获取 channel
		redisUtil.set(String.format(KeyConstant.PREFIX_WX_OPENID_SCENE, openId), qrSceneId, 5, TimeUnit.MINUTES);
		// 推送消息, 让前端感知到扫码事件, 并且正在授权
		webSocketService.handleAuthorizing(qrSceneId);

		// 注意: 这个事件处理方法是需要触发用户登录授权的, 所以无论是否扫码关注还是扫码授权都应该有这个授权地址的返回
		String authorizeUrl = String.format(AUTHORIZE_URL, wxMpService.getWxMpConfigStorage().getAppId(),
				URLEncoder.encode(callback + "/wx/portal/callBack", StandardCharsets.UTF_8));
		log.info("[微信]扫码授权地址：{}", authorizeUrl);
		return new TextBuilder().build("请点击链接授权：<a href=\"" + authorizeUrl + "\">登录</a>",
				wxMpXmlMessage, wxMpService);
	}
}
