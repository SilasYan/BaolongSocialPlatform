package icu.baolong.social.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Jackson 序列化配置
 *
 * @author Silas Yan 2025-04-24 22:36
 */
@JsonComponent
public class JsonConfig {

	@Bean
	public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
		SimpleModule module = new SimpleModule();
		module.addSerializer(Long.class, ToStringSerializer.instance);
		module.addSerializer(Long.TYPE, ToStringSerializer.instance);
		objectMapper.registerModule(module);
		return objectMapper;
	}
}
