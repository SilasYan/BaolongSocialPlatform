package icu.baolong.social.cache;

import icu.baolong.social.module.sys.dao.SysConfigDao;
import icu.baolong.social.repository.sys.entity.SysConfig;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统配置缓存
 *
 * @author Silas Yan 2025-05-25 16:04
 */
@Component
@CacheConfig(cacheNames = "configs")
public class SysConfigCache {

	@Resource
	private SysConfigDao sysConfigDao;

	/**
	 * 获取系统配置
	 *
	 * @param configKey 配置键
	 * @return 系统配置对象
	 */
	@Cacheable(key = "'config:' + #p0")
	public SysConfig getConfig(String configKey) {
		return sysConfigDao.getSysConfigByKey(configKey);
	}
}
