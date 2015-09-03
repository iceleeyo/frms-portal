package cn.com.bsfit.frms.portal.redis;

import redis.clients.jedis.Jedis;

/**
 * redis测试
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
public class RedisTest {

	// redis 主机
	private static final String REDIS_HOST = "10.100.1.20";
	// redis 端口号
	private static final Integer REDIS_PORT = 6379;
	public static Jedis jedis = null;

	/**
	 * 获取redis连接客户端
	 * @return
	 */
	public static Jedis getClient() {
		if (jedis == null) {
			jedis = new Jedis(REDIS_HOST, REDIS_PORT);
		}
		return jedis;
	}
	
	/**
	 * 测试redis保存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String testRedisSet(String key, String value) {
		return getClient().set(key, value);
	}
	
	/**
	 * 测试redis获取
	 * 
	 * @param key
	 * @return
	 */
	public static String testRedisGet(String key) {
		return getClient().get(key);
	}
	
	/**
	 * 测试redis过期时间
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public static Long testRedisExpire(String key, Integer seconds) {
		return getClient().expire(key, seconds);
	}
	
	/**
	 * 测试redis删除
	 * 
	 * @param key
	 * @return
	 */
	public static Long testRedisDel(String key) {
		return getClient().del(key);
	}
	
	public static void main(String[] args) {
//		System.out.println(testRedisSet("123", "测试"));
//		System.out.println(testRedisGet("123"));
//		System.out.println(testRedisExpire("123", 30));
		System.out.println(testRedisDel("1434607544264YWRtaW4="));
	}
}