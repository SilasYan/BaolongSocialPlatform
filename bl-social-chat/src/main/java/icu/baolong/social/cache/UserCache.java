package icu.baolong.social.cache;

import icu.baolong.social.constants.KeyConstant;
import icu.baolong.social.common.utils.RedisUtil;
import icu.baolong.social.function.cache.AbstractRedisCache;
import icu.baolong.social.module.blacklist.dao.BlacklistDao;
import icu.baolong.social.module.user.dao.UserDao;
import icu.baolong.social.repository.blacklist.entity.Blacklist;
import icu.baolong.social.repository.user.entity.User;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户缓存
 *
 * @author Silas Yan 2025-05-29 21:10
 */
@Slf4j
@Component
@CacheConfig(cacheNames = "user")
public class UserCache extends AbstractRedisCache<Long, User> {

	@Resource
	private UserDao userDao;
	@Resource
	private RedisUtil redisUtil;
	@Resource
	private BlacklistDao blacklistDao;

	/**
	 * 获取黑名单
	 *
	 * @return 黑名单
	 */
	@Cacheable(key = "'blacklist'")
	public Set<String> getBlacklist() {
		return blacklistDao.list().stream().map(Blacklist::getBlackTarget).collect(Collectors.toSet());
	}

	/**
	 * 清空黑名单缓存
	 */
	@CacheEvict(key = "'blacklist'")
	public void evictBlacklist() {}

	// region 重写父类方法

	/**
	 * 获取 RedisUtil
	 *
	 * @return RedisUtil
	 */
	@Override
	protected RedisUtil redisUtil() {
		return redisUtil;
	}

	/**
	 * 获取缓存 KEY
	 *
	 * @param userId 用户ID
	 * @return 缓存 KEY
	 */
	@Override
	protected String key(Long userId) {
		return String.format(KeyConstant.PREFIX_USER_INFO, userId);
	}

	/**
	 * 加载
	 *
	 * @param userIdList 用户ID列表
	 * @return 缓存 MAP
	 */
	@Override
	protected Map<Long, User> load(List<Long> userIdList) {
		List<User> users = userDao.listByIds(userIdList);
		return users.stream().collect(Collectors.toMap(User::getUserId, user -> user));
	}

	// endregion 重写父类方法
}
