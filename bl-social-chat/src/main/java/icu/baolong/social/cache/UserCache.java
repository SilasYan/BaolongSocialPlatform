package icu.baolong.social.cache;

import icu.baolong.social.module.blacklist.dao.BlacklistDao;
import icu.baolong.social.repository.blacklist.entity.Blacklist;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

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
public class UserCache {

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
}
