package icu.baolong.social.weixin.service.impl;

import icu.baolong.social.common.exception.BusinessException;
import icu.baolong.social.common.response.RespCode;
import icu.baolong.social.common.utils.RedisUtil;
import icu.baolong.social.constants.KeyConstant;
import icu.baolong.social.weixin.service.WeiXinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.stereotype.Service;

/**
 * 微信相关接口 - 微信交互实现
 *
 * @author Silas Yan 2025-05-23 21:44
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeiXinInteractiveService implements WeiXinService {

	private final WxMpService wxService;
	private final RedisUtil redisUtil;

	/**
	 * 获取并自增场景ID
	 *
	 * @return 自增后的场景ID
	 */
	@Override
	public Integer getQrSceneId() {
		Integer sceneId = redisUtil.get(KeyConstant.KEY_WX_SCENE_ID);
		long increment = redisUtil.increment(KeyConstant.KEY_WX_SCENE_ID, sceneId == null ? 100000 : 1);
		return Integer.parseInt(String.valueOf(increment));
	}

	/**
	 * 获取微信临时二维码
	 *
	 * @param qrSceneId 场景ID
	 * @return 二维码地址
	 */
	@Override
	public String getWxTempQrCode(Integer qrSceneId) {
		log.info("[微信]获取二维码, sceneId: {}", qrSceneId);
		try {
			WxMpQrCodeTicket wxMpQrCodeTicket = wxService.getQrcodeService().qrCodeCreateTmpTicket(qrSceneId, 120);
			return wxMpQrCodeTicket.getUrl();
		} catch (WxErrorException e) {
			log.error("[微信]获取二维码失败！", e);
			throw new BusinessException(RespCode.SYSTEM_ERROR);
		}
	}
}
