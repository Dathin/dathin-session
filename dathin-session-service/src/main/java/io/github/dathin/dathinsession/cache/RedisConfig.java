package io.github.dathin.dathinsession.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

	@Bean
	@Primary
	public JedisPool getJedisPool(@Value("${token.redis.host}") String redisHost) {
		var jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setJmxEnabled(false);
		return new JedisPool(jedisPoolConfig, redisHost, 6379);
	}

}
