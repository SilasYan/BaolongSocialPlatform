package icu.baolong.social.module.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import icu.baolong.social.cache.ItemsCache;
import icu.baolong.social.common.converter.ConvertUtils;
import icu.baolong.social.common.utils.ServletUtil;
import icu.baolong.social.entity.constants.CacheConstant;
import icu.baolong.social.entity.constants.KeyConstant;
import icu.baolong.social.entity.constants.TextConstant;
import icu.baolong.social.common.exception.BusinessException;
import icu.baolong.social.common.exception.ThrowUtil;
import icu.baolong.social.events.UserEventPublisher;
import icu.baolong.social.manager.email.EmailManager;
import icu.baolong.social.common.response.RespCode;
import icu.baolong.social.common.utils.RedisUtil;
import icu.baolong.social.module.sys.service.SysConfigService;
import icu.baolong.social.module.user.adapter.UserAdapter;
import icu.baolong.social.module.items.domain.enums.ItemTypeEnum;
import icu.baolong.social.module.user.dao.UserBackpackDao;
import icu.baolong.social.module.user.domain.enums.UserSexEnum;
import icu.baolong.social.module.user.domain.request.UserLoginReq;
import icu.baolong.social.module.user.domain.response.UserBadgeResp;
import icu.baolong.social.module.user.domain.response.UserInfoResp;
import icu.baolong.social.module.user.domain.enums.UserDisabledEnum;
import icu.baolong.social.module.user.service.UserLoginLogService;
import icu.baolong.social.repository.items.entity.Items;
import icu.baolong.social.repository.user.entity.User;
import icu.baolong.social.module.user.dao.UserDao;
import icu.baolong.social.module.user.domain.request.UserRegisterReq;
import icu.baolong.social.module.user.domain.response.EmailCodeResp;
import icu.baolong.social.module.user.domain.response.GraphicCodeResp;
import icu.baolong.social.module.user.service.UserService;
import icu.baolong.social.repository.user.entity.UserBackpack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 用户表 (user) - 服务实现
 *
 * @author Silas Yan 2025-05-21 22:22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final RedisUtil redisUtil;
	private final UserDao userDao;
	private final EmailManager emailManager;
	private final SysConfigService sysConfigService;
	private final UserLoginLogService userLoginLogService;
	private final UserBackpackDao userBackpackDao;
	private final ItemsCache itemsCache;
	private final TransactionTemplate transactionTemplate;
	private final ApplicationEventPublisher eventPublisher;

	/**
	 * 发送邮箱验证码
	 *
	 * @param userEmail 用户邮箱
	 * @return 邮箱验证码响应
	 */
	@Override
	public EmailCodeResp sendEmailCode(String userEmail) {
		// 根据邮箱判断用户是否存在, 存在抛异常
		boolean exists = userDao.existsUserByUserEmail(userEmail);
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
		boolean exists = userDao.existsUserByUserEmail(userEmail);
		ThrowUtil.tif(exists, RespCode.FAILED, "邮箱已被注册");
		// 构建用户对象
		String passwordSalt = sysConfigService.getConfigAsValue(CacheConstant.PASSWORD_SALT);
		String defaultPassword = sysConfigService.getConfigAsValue(CacheConstant.DEFAULT_PASSWORD);
		String defaultAccountPrefix = sysConfigService.getConfigAsValue(CacheConstant.DEFAULT_ACCOUNT_PREFIX);
		User user = UserAdapter.buildDefaultUser(userEmail, this.encryptPassword(passwordSalt, defaultPassword), defaultAccountPrefix);
		// 保存用户信息
		boolean result = userDao.save(user);
		ThrowUtil.tif(!result, "注册失败");
		emailManager.sendEmailAsRegisterSuccess(userEmail, "注册成功 - 暴龙社交平台", defaultPassword);
		eventPublisher.publishEvent(new UserEventPublisher(this, user));
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
		return GraphicCodeResp.builder().captchaKey(codeKey).imageBase64(imageBase64).build();
	}

	/**
	 * 用户登录
	 *
	 * @param userLoginReq 用户登录请求
	 * @return Token
	 */
	@Override
	public String userLogin(UserLoginReq userLoginReq) {
		String wxOpenId = userLoginReq.getWxOpenId();
		User user = null;
		if (StrUtil.isBlank(wxOpenId)) {
			String account = userLoginReq.getAccount();
			user = userDao.getUserByAccount(account);
		} else {
			user = this.getUserByWxOpenId(wxOpenId);
		}
		if (user == null) {
			log.error("[用户登录] 登录失败! {}", TextConstant.ERROR_USER_OR_PASSWORD);
			throw new BusinessException(TextConstant.ERROR_USER_OR_PASSWORD);
		}
		if (UserDisabledEnum.isDisabled(user.getIsDisabled())) {
			log.error("[用户登录] 登录失败! {}", TextConstant.ERROR_USER_STATUS);
			throw new BusinessException(TextConstant.ERROR_USER_STATUS);
		}
		// 只有通过账号/邮箱+密码登录, 才校验密码
		if (StrUtil.isBlank(wxOpenId)) {
			String passwordSalt = sysConfigService.getConfigAsValue(CacheConstant.PASSWORD_SALT);
			if (!user.getUserPassword().equals(this.encryptPassword(passwordSalt, userLoginReq.getUserPassword()))) {
				log.error("[用户登录] 登录失败! {}", TextConstant.ERROR_USER_OR_PASSWORD);
				throw new BusinessException(TextConstant.ERROR_USER_OR_PASSWORD);
			}
		}
		// 记录登录态, 并把当前用户信息放入到redis中, 7天过期
		Long userId = user.getId();
		StpUtil.login(userId);
		redisUtil.set(String.format(KeyConstant.PREFIX_USER_INFO, userId), user, 7, TimeUnit.DAYS);
		// 异步记录日志
		userLoginLogService.recordLoginLog(userId, new Date(), ServletUtil.getIp(), ServletUtil.getHeader("User-Agent"));
		return StpUtil.getTokenValue();
	}

	/**
	 * 用户登录（扫码登录）
	 *
	 * @param wxOpenId 微信OpenId
	 * @return Token
	 */
	@Override
	public String userLoginByScanQrCode(String wxOpenId) {
		UserLoginReq userLoginReq = new UserLoginReq();
		userLoginReq.setWxOpenId(wxOpenId);
		return this.userLogin(userLoginReq);
	}

	/**
	 * 用户登出
	 *
	 * @return 是否成功
	 */
	@Override
	public Boolean userLogout() {
		Long userId = StpUtil.getLoginIdAsLong();
		redisUtil.delete(String.format(KeyConstant.PREFIX_USER_INFO, userId));
		StpUtil.logout(userId);
		log.info("[用户注销] 清除用户 [{}] 登录态成功!", userId);
		return true;
	}

	/**
	 * 获取登录用户信息
	 *
	 * @return 用户信息响应
	 */
	@Override
	public UserInfoResp getUserInfoByLogin() {
		Long userId = StpUtil.getLoginIdAsLong();
		User user = userDao.getById(userId);
		ThrowUtil.tif(ObjectUtil.isNull(user), "用户不存在");
		return ConvertUtils.from(UserInfoResp.class, user, "userId");
	}

	/**
	 * 根据用户ID获取用户信息
	 *
	 * @param userId 用户ID
	 * @return 用户对象
	 */
	@Override
	public User getUserByUserId(Long userId) {
		return userDao.getOne(userDao.lambdaQueryWrapper().eq(User::getId, userId));
	}

	/**
	 * 根据微信OpenId获取用户信息
	 *
	 * @param wxOpenId 微信OpenId
	 * @return 用户对象
	 */
	@Override
	public User getUserByWxOpenId(String wxOpenId) {
		return userDao.getOne(userDao.lambdaQueryWrapper().eq(User::getWxOpenId, wxOpenId));
	}

	/**
	 * 根据用户名称获取用户信息
	 *
	 * @param userName 用户名称
	 * @return 用户对象
	 */
	@Override
	public User getUserByUserName(String userName) {
		return userDao.getOne(userDao.lambdaQueryWrapper().eq(User::getUserName, userName));
	}

	/**
	 * 根据微信OpenId注册用户
	 *
	 * @param openId 微信OpenId
	 */
	@Override
	public void userRegisterByWxOpenId(String openId) {
		String passwordSalt = sysConfigService.getConfigAsValue(CacheConstant.PASSWORD_SALT);
		String defaultPassword = sysConfigService.getConfigAsValue(CacheConstant.DEFAULT_PASSWORD);
		User user = UserAdapter.buildUserByWxOpenId(openId, this.encryptPassword(passwordSalt, defaultPassword));
		userDao.save(user);
		// 用户注册后的事件, 发放物品
		eventPublisher.publishEvent(new UserEventPublisher(this, user));
	}

	/**
	 * 微信授权登录
	 *
	 * @param wxOpenId   微信OpenId
	 * @param userName   微信用户昵称
	 * @param userAvatar 微信头像
	 * @param userSex    微信性别
	 * @return 用户对象
	 */
	@Override
	public User userUpdateByWxAuthorize(String wxOpenId, String userName, String userAvatar, Integer userSex) {
		User user = this.getUserByWxOpenId(wxOpenId);
		boolean update = false;
		if (StrUtil.isBlank(user.getUserAccount())) {
			String defaultAccountPrefix = sysConfigService.getConfigAsValue(CacheConstant.DEFAULT_ACCOUNT_PREFIX);
			String account = defaultAccountPrefix + RandomUtil.randomString(8).toUpperCase();
			user.setUserAccount(account);
			update = true;
		}
		if (StrUtil.isBlank(user.getUserName())) {
			user.setUserName(userName);
			update = true;
		}
		if (StrUtil.isBlank(user.getUserAvatar())) {
			user.setUserAvatar(userAvatar);
			update = true;
		}
		if (UserSexEnum.OTHER.getKey().equals(user.getUserSex())) {
			user.setUserSex(userSex);
			update = true;
		}
		if (update) {
			userDao.updateById(user);
		}
		return user;
	}

	/**
	 * 修改用户名称
	 *
	 * @param userName 用户名称
	 */
	@Override
	public void modifyUserName(String userName) {
		long userId = StpUtil.getLoginIdAsLong();
		// TODO 敏感词过滤
		// 先判断用户名称是否存在
		User user = this.getUserByUserName(userName);
		ThrowUtil.tif(ObjectUtil.isNotNull(user), "名称已被占用啦~");
		// 获取是否有改名卡
		Items items = itemsCache.getItemsListByType(ItemTypeEnum.NAME_CHANGE_CARD.getKey()).getFirst();
		UserBackpack userBackpack = userBackpackDao.getOneValidNameChangeCard(userId, items.getId());
		ThrowUtil.tif(ObjectUtil.isNull(userBackpack), "暂时没有改名卡, 请联系管理员获取~");
		// 操作数据库, 使用事务
		transactionTemplate.execute(status -> {
			// 把当前的改名卡进行使用
			boolean result = userBackpackDao.useBackpackItem(userBackpack.getId());
			if (result) {
				// 修改用户名称
				result = userDao.updateUserNameById(userId, userName);
			}
			ThrowUtil.tif(!result, "改名失败, 请联系管理员~");
			return true;
		});
	}

	/**
	 * 获取徽章列表
	 *
	 * @return 徽章列表
	 */
	@Override
	public List<UserBadgeResp> getBadgeList() {
		long userId = StpUtil.getLoginIdAsLong();
		// 获取所有徽章
		List<Items> badgeList = itemsCache.getItemsListByType(ItemTypeEnum.BADGE.getKey());
		// 获取当前用户徽章
		List<UserBackpack> userBackpacks = userBackpackDao.getBadgeListByUserIdAndItemIds(userId, badgeList.stream().map(Items::getId).toList());
		// 获取当前用户佩戴的徽章
		User user = this.getUserByUserId(userId);
		return UserAdapter.buildUserBadgeRespList(user, badgeList, userBackpacks);
	}

	/**
	 * 修改用户徽章
	 *
	 * @param badgeId 徽章ID
	 */
	@Override
	public void modifyUserBadge(Long badgeId) {
		long userId = StpUtil.getLoginIdAsLong();
		// 查询当前的物品ID是否是徽章
		Items items = itemsCache.getItemsById(badgeId);
		ThrowUtil.tif(ObjectUtil.isNull(items), "徽章不存在哦~");
		ThrowUtil.tif(!ItemTypeEnum.BADGE.getKey().equals(items.getItemType()), "徽章无效~");
		// 获取是否获得了该徽章
		UserBackpack userBackpack = userBackpackDao.getBadgeByUserIdAndItemId(userId, badgeId);
		ThrowUtil.tif(ObjectUtil.isNull(userBackpack), "您还没有获得该徽章哦~");
		// 佩戴徽章
		boolean result = userDao.updateBadgeIdById(userId, badgeId);
		ThrowUtil.tif(!result, "佩戴徽章失败, 请联系管理员~");
	}

	// region 私有方法

	/**
	 * 加密密码
	 *
	 * @param salt           盐值
	 * @param originPassword 原密码
	 * @return 加密后密码
	 */
	private String encryptPassword(String salt, String originPassword) {
		return DigestUtils.md5DigestAsHex((salt + originPassword).getBytes());
	}

	// endregion 私有方法
}
