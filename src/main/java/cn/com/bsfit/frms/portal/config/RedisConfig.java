package cn.com.bsfit.frms.portal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis配置
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@Configuration
@EnableConfigurationProperties
@ConditionalOnClass({ JedisConnection.class, RedisOperations.class, Jedis.class })
public class RedisConfig {
	
	@Value("${redis.host}")
	private String host;
	@Value("${redis.port}")
	private int port;
	@Value("${redis.pass}")
	private String pass;
	@Value("${redis.maxIdle}")
	private int maxIdle;
	@Value("${redis.maxTotal}")
	private int maxTotal;
	@Value("${redis.maxWaitMillis}")
	private int maxWaitMillis;
	@Value("${redis.testOnBorrow}")
	private boolean testOnBorrow;

	@Bean
	@ConfigurationProperties(prefix = "redis")
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		return jedisPoolConfig;
	}

	@Autowired
	private JedisPoolConfig jedisPoolConfig;

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(host);
		jedisConnectionFactory.setPort(port);
		// jedisConnectionFactory.setPassword(pass);
		jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
		return jedisConnectionFactory;
	}

	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;

	@Bean
	@SuppressWarnings("rawtypes")
	public RedisTemplate<?, ?> redisTemplate() {
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		return redisTemplate;
	}
}