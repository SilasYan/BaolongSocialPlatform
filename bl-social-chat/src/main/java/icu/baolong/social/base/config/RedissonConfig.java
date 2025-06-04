package icu.baolong.social.base.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson配置类
 *
 * @author Silas Yan 2025-05-22 22:50
 */
@Configuration
public class RedissonConfig {

	@Bean
	public RedissonClient redissonClient(RedisProperties redisProperties) {
		Config config = new Config();
		config.useSingleServer()
				.setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
				.setPassword(redisProperties.getPassword())
				.setDatabase(redisProperties.getDatabase());
		return Redisson.create(config);
	}
}
