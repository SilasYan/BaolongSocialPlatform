package icu.baolong.social.controller;

import cn.hutool.core.util.StrUtil;
import icu.baolong.social.common.exception.ThrowUtil;
import icu.baolong.social.function.limit.Limit;
import icu.baolong.social.function.limit.LimitType;
import icu.baolong.social.base.response.BaseResponse;
import icu.baolong.social.base.response.Result;
import icu.baolong.social.service.weixin.service.WeiXinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 微信交互接口
 *
 * @author Silas Yan 2025-05-23 20:14
 */
@Slf4j
@Tag(name = "微信接口", description = "微信相关交互接口")
@RestController
@RequestMapping("/wx/portal")
public class WxPortalController {

	@Resource
	private WeiXinService weiXinInteractiveService;
	@Resource
	private WeiXinService weiXinPortalService;

	@Limit(limitType = LimitType.IP)
	@Operation(summary = "获取临时二维码", description = "获取临时二维码")
	@GetMapping("/tempQrCode")
	public BaseResponse<String> getTempQrCode() {
		return Result.success(weiXinInteractiveService.getWxTempQrCode(weiXinInteractiveService.getQrSceneId()));
	}

	@Operation(summary = "扫码授权回调", description = "微信回调接口")
	@GetMapping("/callBack")
	public RedirectView callBack(@RequestParam("code") String code) {
		ThrowUtil.tif(StrUtil.isBlank(code), "code不能为空");
		return weiXinPortalService.callBack(code);
	}

	@Operation(summary = "微信认证接口", description = "微信认证接口")
	@GetMapping(produces = "text/plain;charset=utf-8")
	public String authGet(@RequestParam(name = "signature", required = false) String signature,
						  @RequestParam(name = "timestamp", required = false) String timestamp,
						  @RequestParam(name = "nonce", required = false) String nonce,
						  @RequestParam(name = "echostr", required = false) String echostr) {
		return weiXinPortalService.authGet(signature, timestamp, nonce, echostr);
	}

	@Operation(summary = "微信消息处理接口", description = "微信消息处理接口")
	@PostMapping(produces = "application/xml; charset=UTF-8")
	public String post(@RequestBody String requestBody,
					   @RequestParam("signature") String signature,
					   @RequestParam("timestamp") String timestamp,
					   @RequestParam("nonce") String nonce,
					   @RequestParam("openid") String openid,
					   @RequestParam(name = "encrypt_type", required = false) String encType,
					   @RequestParam(name = "msg_signature", required = false) String msgSignature) {
		return weiXinPortalService.post(openid, signature, encType, msgSignature, timestamp, nonce, requestBody);
	}
}
