package icu.baolong.social.weixin.handler;

import java.util.Map;

import icu.baolong.social.weixin.builder.TextBuilder;
import icu.baolong.social.weixin.service.WeiXinService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Slf4j
@Component
public class SubscribeHandler extends AbstractHandler {

	@Resource
	private WeiXinService weiXinEventService;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> context, WxMpService wxMpService,
									WxSessionManager sessionManager) throws WxErrorException {
		log.info("[微信]关注事件! openId: {}", wxMpXmlMessage.getFromUser());
		WxMpXmlOutMessage responseResult = weiXinEventService.scanHandle(wxMpXmlMessage, wxMpService);
		if (responseResult != null) {
			return responseResult;
		}
		return new TextBuilder().build("感谢关注!", wxMpXmlMessage, wxMpService);
	}
}
