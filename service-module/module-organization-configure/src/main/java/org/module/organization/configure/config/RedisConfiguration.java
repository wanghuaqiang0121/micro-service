package org.module.organization.configure.config;

import java.util.HashMap;
import java.util.Map;

import org.module.organization.configure.global.BaseGlobal;
import org.service.redis.cache.RedisCacheManager;
import org.service.redis.cache.RedisConfig;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {

	@Bean
	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
		RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
		rcm.setDefaultExpiration(1800L);
		Map<String, RedisConfig> redisMap = new HashMap<>();
		redisMap.put(BaseGlobal.CACHE_ORGANIZATION_USER, new RedisConfig(1800L, true));
		rcm.setExpires(redisMap);
		return rcm;
	}

	@Bean
	public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<?, ?> template = new StringRedisTemplate(factory);
		Jackson2JsonRedisSerializer<?> redisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		redisSerializer.setObjectMapper(om);
		template.setValueSerializer(redisSerializer);
		template.setHashValueSerializer(redisSerializer);
		template.afterPropertiesSet();
		return template;
	}
}