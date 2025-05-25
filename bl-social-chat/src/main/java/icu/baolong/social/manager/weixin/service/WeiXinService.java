package icu.baolong.social.manager.weixin.service;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 微信相关接口
 *
 * @author Silas Yan 2025-05-24 18:46
 */
public interface WeiXinService {

	/**
	 * 获取并自增场景ID
	 *
	 * @return 自增后的场景ID
	 */
	default Integer getQrSceneId() {return null;}

	/**
	 * 获取微信临时二维码
	 *
	 * @param qrSceneId 场景ID
	 * @return 二维码地址
	 */
	default String getWxTempQrCode(Integer qrSceneId) {return null;}

	/**
	 * 扫码授权回调
	 *
	 * @param code code
	 * @return 重定向
	 */
	default RedirectView callBack(String code) {return null;}

	/**
	 * 微信认证接口
	 *
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	default String authGet(String signature, String timestamp, String nonce, String echostr) {return null;}

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
	default String post(String openid, String signature, String encType, String msgSignature,
						String timestamp, String nonce, String requestBody) {return null;}

	/**
	 * 扫描事件处理
	 *
	 * @param wxMpXmlMessage 微信消息对象
	 * @param wxMpService    微信SDK服务对象
	 * @return 处理后的消息
	 */
	default WxMpXmlOutMessage scanHandle(WxMpXmlMessage wxMpXmlMessage, WxMpService wxMpService) {return null;}
}
