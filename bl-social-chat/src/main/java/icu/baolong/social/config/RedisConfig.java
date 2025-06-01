package icu.baolong.social.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置
 *
 * @author Silas Yan 2025-04-24 23:17
 */
@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		// ObjectMapper是Jackson的一个工作类，作用是将JSON映射到Java对象即反序列化，或将Java对象映射到JSON即序列化
		ObjectMapper objectMapper = new ObjectMapper();
		// 设置序列化时的可见性，第一个参数是选择序列化哪些属性，比如时序列化setter还是getter；第二个参数是选择哪些修饰符权限的属性来序列化
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		// 设置出现故障即错误的类型，是指验证程序，此时的参数为无需验证
		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
				ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

		// 使用JSON的序列化，配置key和value的序列化器
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
				new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);

		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// key采用String的序列化方式
		redisTemplate.setKeySerializer(stringRedisSerializer);
		// value序列化方式采用jackson
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

		// hash的key也采用String的序列化方式
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		// hash的value序列化方式采用jackson
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}
}
