package icu.baolong.social.manager.weixin.service.impl;

import cn.hutool.json.JSONUtil;
import icu.baolong.social.common.exception.BusinessException;
import icu.baolong.social.common.response.RespCode;
import icu.baolong.social.common.utils.RedisUtil;
import icu.baolong.social.entity.constants.KeyConstant;
import icu.baolong.social.module.user.service.UserService;
import icu.baolong.social.repository.user.entity.User;
import icu.baolong.social.manager.websocket.service.WebSocketService;
import icu.baolong.social.manager.weixin.service.WeiXinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 微信相关接口 - 微信入口实现
 *
 * @author Silas Yan 2025-05-23 21:44
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeiXinPortalService implements WeiXinService {

	private final WxMpService wxService;
	private final WxMpMessageRouter messageRouter;
	private final UserService userService;
	private final RedisUtil redisUtil;
	private final WebSocketService webSocketService;

	/**
	 * 扫码授权回调
	 *
	 * @param code code
	 * @return 重定向
	 */
	@Override
	public RedirectView callBack(String code) {
		try {
			log.info("[微信]扫码授权回调, code: {}", code);
			// 获取accessToken
			WxOAuth2AccessToken accessToken = wxService.getOAuth2Service().getAccessToken(code);
			// 获取用户信息
			WxOAuth2UserInfo userInfo = wxService.getOAuth2Service().getUserInfo(accessToken, "zh_CN");
			log.info("[微信]扫码授权回调, UserInfo: {}", JSONUtil.toJsonStr(userInfo));
			// 用户微信授权并更新信息
			User user = userService.userUpdateByWxAuthorize(userInfo.getOpenid(), userInfo.getNickname(),
					userInfo.getHeadImgUrl(), userInfo.getSex());
			// 通过 openId 获取 qrSceneId
			Integer qrSceneId = redisUtil.get(String.format(KeyConstant.PREFIX_WX_OPENID_SCENE, userInfo.getOpenid()));
			// 登录, 获取Token
			String token = userService.userLoginByScanQrCode(userInfo.getOpenid());
			// 处理授权成功
			webSocketService.handleAuthorizeSuccess(qrSceneId, user, token);
			// 重定向
			RedirectView redirectView = new RedirectView();
			redirectView.setUrl("https://blog.baolong.icu");
			return redirectView;
		} catch (WxErrorException e) {
			if (e.getError().getErrorCode() == 48001) {
				log.error("[微信]该公众号没有获取用户信息权限！", e);
				throw new BusinessException(RespCode.SYSTEM_ERROR, "该公众号没有获取用户信息权限");
			}
			log.error("[微信]扫码授权回调失败！", e);
			throw new BusinessException(RespCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 微信认证接口
	 *
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	@Override
	public String authGet(String signature, String timestamp, String nonce, String echostr) {
		log.info("[微信]微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
		if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
			log.error("[微信]请求参数非法！");
			throw new BusinessException(RespCode.SYSTEM_ERROR, "请求参数非法");
		}
		if (wxService.checkSignature(timestamp, nonce, signature)) {
			return echostr;
		}
		return "非法请求";
	}

	/**
	 * 微信消息处理接口
	 *
	 * @param openid
	 * @param signature
	 * @param encType
	 * @param msgSignature
	 * @param timestamp
	 * @param nonce
	 * @param requestBody
	 * @return
	 */
	@Override
	public String post(String openid, String signature, String encType, String msgSignature,
					   String timestamp, String nonce, String requestBody) {
		log.info("""
						[微信]接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],\
						 timestamp=[{}], nonce=[{}], requestBody=[
						{}
						]\s""",
				openid, signature, encType, msgSignature, timestamp, nonce, requestBody);

		if (!wxService.checkSignature(timestamp, nonce, signature)) {
			log.error("[微信]非法请求，可能属于伪造的请求！");
			throw new BusinessException(RespCode.SYSTEM_ERROR);
		}

		String out = null;
		if (encType == null) {
			// 明文传输的消息
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
			WxMpXmlOutMessage outMessage = this.messageRouter.route(inMessage);
			if (outMessage == null) {
				return "";
			}

			out = outMessage.toXml();
		} else if ("aes".equalsIgnoreCase(encType)) {
			// aes加密的消息
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxService.getWxMpConfigStorage(),
					timestamp, nonce, msgSignature);
			log.info("[微信]消息解密后内容为: {} ", inMessage.toString());
			WxMpXmlOutMessage outMessage = this.messageRouter.route(inMessage);
			if (outMessage == null) {
				return "";
			}

			out = outMessage.toEncryptedXml(wxService.getWxMpConfigStorage());
		}

		log.info("[微信]组装回复信息: {}", out);
		return out;
	}
}
