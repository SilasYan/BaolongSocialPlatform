package icu.baolong.social.manager.weixin.handler;

import java.util.Map;

import icu.baolong.social.manager.weixin.service.WeiXinService;
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
public class ScanHandler extends AbstractHandler {

	@Resource
	private WeiXinService weiXinEventService;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
									WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
		log.info("[微信]扫码事件! openId: {}", wxMpXmlMessage.getFromUser());
		return weiXinEventService.scanHandle(wxMpXmlMessage, wxMpService);
	}
}
