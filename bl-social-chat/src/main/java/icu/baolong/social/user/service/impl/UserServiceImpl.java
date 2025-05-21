package icu.baolong.social.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import icu.baolong.social.common.constants.KeyConstant;
import icu.baolong.social.common.constants.TextConstant;
import icu.baolong.social.common.exception.BusinessException;
import icu.baolong.social.common.exception.ThrowUtil;
import icu.baolong.social.common.manager.EmailManager;
import icu.baolong.social.common.response.RespCode;
import icu.baolong.social.common.utils.RedisUtil;
import icu.baolong.social.user.dao.UserDao;
import icu.baolong.social.user.domain.entity.User;
import icu.baolong.social.user.domain.request.UserRegisterReq;
import icu.baolong.social.user.domain.response.EmailCodeResp;
import icu.baolong.social.user.domain.response.GraphicCodeResp;
import icu.baolong.social.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 用户表 (user) - 服务实现
 *
 * @author Silas Yan 2025-05-21 22:22
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final RedisUtil redisUtil;
	private final UserDao userDao;
	private final EmailManager emailManager;

	/**
	 * 发送邮箱验证码
	 *
	 * @param userEmail 用户邮箱
	 * @return 邮箱验证码响应
	 */
	@Override
	public EmailCodeResp sendEmailCode(String userEmail) {
		// 校验当前邮箱是否存在, 存在抛异常
		boolean exists = userDao.exists(userDao.lambdaQueryWrapper().eq(User::getUserEmail, userEmail));
		ThrowUtil.tif(exists, RespCode.FAILED, "邮箱已被注册");
		// 发送邮箱验证码, 这里是异步发送的
		String code = RandomUtil.randomNumbers(6);
		emailManager.sendEmailAsEmailCode(userEmail, "注册验证码 - 暴龙社交平台", code);
		// 生成验证码KEY, 并存入到redis中, 5分钟过期
		String codeKey = UUID.randomUUID().toString();
		redisUtil.set(String.format(KeyConstant.PREFIX_EMAIL_CODE, codeKey), code, 5, TimeUnit.MINUTES);
		return EmailCodeResp.builder().codeKey(codeKey).build();
	}

	/**
	 * 用户注册
	 *
	 * @param userRegisterReq 用户注册请求
	 * @return 是否成功
	 */
	@Override
	public Boolean userRegister(UserRegisterReq userRegisterReq) {
		// 校验邮箱验证码KEY是否存在, 以及验证码正确性, 不符合则抛异常
		String codeKey = String.format(KeyConstant.PREFIX_EMAIL_CODE, userRegisterReq.getCodeKey());
		String redisCodeKey = redisUtil.get(codeKey);
		if (StrUtil.isEmpty(redisCodeKey) || !redisCodeKey.equals(userRegisterReq.getCodeValue())) {
			throw new BusinessException(TextConstant.ERROR_ILLEGAL_PARAMETER);
		}
		redisUtil.delete(codeKey);
		// 校验当前邮箱是否存在, 存在抛异常
		String userEmail = userRegisterReq.getUserEmail();
		boolean exists = userDao.exists(userDao.lambdaQueryWrapper().eq(User::getUserEmail, userEmail));
		ThrowUtil.tif(exists, RespCode.FAILED, "邮箱已被注册");
		// 构建用户对象
		String password = "123456";
		User user = new User()
				.setUserEmail(userEmail)
				.setUserPassword(DigestUtils.md5DigestAsHex(("baolong" + password).getBytes()));
		this.fillUserDefaultField(user);
		// 保存用户信息
		boolean result = userDao.save(user);
		ThrowUtil.tif(!result, "注册失败");
		emailManager.sendEmailAsRegisterSuccess(userEmail, "注册成功 - 暴龙社交平台", password);
		return true;
	}

	/**
	 * 获取图形验证码
	 *
	 * @return 图形验证码响应
	 */
	@Override
	public GraphicCodeResp getGraphicCode() {
		// 构建图形验证码
		SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
		specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
		String code = specCaptcha.text().toLowerCase();
		String imageBase64 = specCaptcha.toBase64();
		// 生成验证码KEY, 并存入到redis中, 1分钟过期
		String codeKey = UUID.randomUUID().toString();
		redisUtil.set(String.format(KeyConstant.PREFIX_GRAPHIC_CODE, codeKey), code, 1, TimeUnit.MINUTES);
		return GraphicCodeResp.builder().codeKey(codeKey).imageBase64(imageBase64).build();
	}

	/**
	 * 填充用户默认字段
	 *
	 * @param user 用户对象
	 */
	private void fillUserDefaultField(User user) {
		String random = RandomUtil.randomString(8).toUpperCase();
		user.setUserAccount(user.getUserEmail())
				.setUserName("user." + random)
				.setUserAvatar("默认头像...")
				.setUserProfile("用户暂未填写个人简介~")
				.setShareCode(random);
	}
}
