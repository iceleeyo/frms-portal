package cn.com.bsfit.frms.portal.bo;

import java.util.List;

/**
 * redis的操作开放接口
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
public interface RedisBO {

	/**
	 * 通过key删除
	 * 
	 * @param key
	 * @return
	 */
	public abstract Long del(final byte[]... keys);
	
	/**
	 * 通过key删除
	 * 
	 * @param key
	 */
	public abstract Long del(final String... keys);
	
	/**
	 * 添加key value 并且设置存活时间(byte)
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public abstract Boolean set(final byte[] key, final byte[] value, final Long liveTime);

	/**
	 * 添加key value 并且设置存活时间
	 * 
	 * @param key
	 * @param value
	 * @param liveTime 单位秒
	 */
	public abstract Boolean set(final String key, final Object value, final Long liveTime);

	/**
	 * 添加key value (字节)(序列化)
	 * 
	 * @param key
	 * @param value
	 */
	public abstract Boolean set(final byte[] key, final byte[] value);
	
	/**
	 * 添加key value
	 * 
	 * @param key
	 * @param value
	 */
	public abstract Boolean set(final String key, final Object value);
	
	/**
	 * 在指定Key所关联的List Value的头部插入参数中给出的所有Values。
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract Long lPush(final byte[] key, final byte[]... value);
	
	/**
	 * 在指定Key所关联的List Value的头部插入参数中给出的所有Values。
	 * 如果该Key不存在，该命令将在插入之前创建一个与该Key关联的空链表，之后再将数据从链表的头部插入。
	 * 如果该键的Value不是链表类型，该命令将返回相关的错误信息。 
	 * 
	 * @param key
	 * @param value
	 * @return 插入后链表中元素的数量
	 */
	public abstract Long lPush(final String key, final Object... value);
	
	/**
	 * 仅有当参数中指定的Key存在时，
	 * 该命令才会在其所关联的List Value的头部插入参数中给出的Value，
	 * 否则将不会有任何操作发生。
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract Long lPushX(final byte[] key, final byte[] value);
	
	/**
	 * 仅有当参数中指定的Key存在时，
	 * 该命令才会在其所关联的List Value的头部插入参数中给出的Value，
	 * 否则将不会有任何操作发生。
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract Long lPushX(final String key, final Object value);
	
	/**
	 * 返回并弹出指定Key关联的链表中的第一个元素，即头部元素。
	 * 如果该Key不存，返回nil。
	 * 
	 * @param key
	 * @return
	 */
	public abstract Object lPop(final byte[] key);
	
	/**
	 * 返回并弹出指定Key关联的链表中的第一个元素，即头部元素。
	 * 如果该Key不存，返回nil。
	 * 
	 * @param key
	 * @return
	 */
	public abstract Object lPop(final String key);
	
	/**
	 * 返回指定Key关联的链表中元素的数量，
	 * 如果该Key不存在，则返回0。
	 * 如果与该Key关联的Value的类型不是链表，则返回相关的错误信息。
	 * 
	 * @param key
	 * @return
	 */
	public abstract Long lLen(final byte[] key);
	
	/**
	 * 返回指定Key关联的链表中元素的数量，
	 * 如果该Key不存在，则返回0。
	 * 如果与该Key关联的Value的类型不是链表，则返回相关的错误信息。
	 * 
	 * @param key
	 * @return
	 */
	public abstract Long lLen(final String key);
	
	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public abstract List<Object> lRange(final byte[] key);
	
	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public abstract List<Object> lRange(final String key);
	
	/**
	 * 获取redis value (Object)
	 * 
	 * @param key
	 * @return
	 */
	public abstract Object get(final byte[] key);
	
	/**
	 * 获取redis value (Object)
	 * 
	 * @param key
	 * @return
	 */
	public abstract Object get(final String key);
	
	/**
	 * 检查key是否已经存在
	 * 
	 * @param key
	 * @return
	 */
	public abstract Boolean exists(final byte[] key);
	
	/**
	 * 检查key是否已经存在
	 * 
	 * @param key
	 * @return
	 */
	public abstract Boolean exists(final String key);
	
	/**
	 * redis内的数据类型
	 * 
	 * @param key
	 * @return
	 */
	public abstract Object type(final byte[] key);
	
	/**
	 * redis内的数据类型
	 * 
	 * @param key
	 * @return
	 */
	public abstract Object type(final String key);

	/**
	 * 清空redis所有数据
	 * 
	 * @return OK
	 */
	public abstract String flushDB();

	/**
	 * 查看redis里有多少数据
	 * 
	 * @return
	 */
	public abstract Long dbSize();

	/**
	 * 检查是否连接成功
	 * 
	 * @return
	 */
	public abstract String ping();
	
	/**
	 * 在某段时间后失效
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public abstract Boolean expire(final byte[] key, final Long seconds);
	
	/**
	 * 在某段时间后失效
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public abstract Boolean expire(final String key, final Long seconds);
	
	/**
	 * 在某个时间点失效
	 * 
	 * @param key
	 * @param unixTime UNIX时间戳 Unix timestamp
	 * @return
	 */
	public abstract Boolean expireAt(final byte[] key, final Long unixTime);
	
	/**
	 * 在某个时间点失效
	 * 
	 * @param key
	 * @param unixTime UNIX时间戳 Unix timestamp
	 * @return
	 */
	public abstract Boolean expireAt(final String key, final Long unixTime);
}