package icu.baolong.social.common.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 本地缓存配置
 *
 * @author Silas Yan 2025-05-25 16:08
 */
@Configuration
@EnableCaching
public class CacheConfig {

	@Bean("caffeineCacheManager")
	public CacheManager caffeineCacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager();
		cacheManager.setCaffeine(Caffeine.newBuilder()
				.expireAfterWrite(5, TimeUnit.MINUTES)
				.initialCapacity(100)
				.maximumSize(200));
		return cacheManager;
	}
}
