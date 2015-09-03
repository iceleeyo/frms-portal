package cn.com.bsfit.frms.portal.bo.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import cn.com.bsfit.frms.portal.bo.RedisBO;

/**
 * 封装redis 缓存服务器服务接口
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@Service
public class RedisBOImpl implements RedisBO {
	
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate<?, ?> redisTemplate;
	
	private RedisBOImpl() {
		
	}
	
	@Override
	public Long del(final byte[]... keys) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				Long result = 0L;
				for (int i = 0; i < keys.length; i++) {
					result = connection.del(keys[i]);
				}
				return result;
			}
		});
	}
	
	@Override
	public Long del(final String... keys) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Long result = 0L;
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                for (int i = 0; i < keys.length; i++) {
                	byte[] byteKeys = serializer.serialize(keys[i]);
                	result = connection.del(byteKeys);
                }
                return result;
            }
        });
	}

	@Override
	public Boolean set(final byte[] key, final byte[] value, final Long liveTime) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    return connection.expire(key, liveTime);
                }
                return true;
            }
        });
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Boolean set(final String key, final Object value, final Long liveTime) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				RedisSerializer serializerObj = redisTemplate.getDefaultSerializer();
				if(liveTime > 0) {
					return set(serializer.serialize(key), serializerObj.serialize(value), liveTime);
				}
				return set(serializer.serialize(key), serializerObj.serialize(value));
			}
		});
	}

	@Override
	public Boolean set(final byte[] key, final byte[] value) {
		return this.set(key, value, 0L);
	}
	
	@Override
	public Boolean set(final String key, final Object value) {
		return this.set(key, value, 0L);
	}

	@Override
	public Long lPush(final byte[] key, final byte[]... value) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.lPush(key, value);
			}
		});
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Long lPush(final String key, final Object... values) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				RedisSerializer serializerObj = redisTemplate.getDefaultSerializer();
				byte[] keys = serializer.serialize(key);
				Long result = 0L;
				for (int i = 0; i < values.length; i++) {
					byte[] byteValues = serializerObj.serialize(values[i]);
					result = connection.lPush(keys, byteValues);
				}
				return result;
			}
		});
	}
	
	@Override
	public Long lPushX(final byte[] key, final byte[] value) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.lPushX(key, value);
			}
		});
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Long lPushX(final String key, final Object value) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				RedisSerializer serializerObj = redisTemplate.getDefaultSerializer();
				return connection.lPushX(serializer.serialize(key), serializerObj.serialize(value));
			}
		});
	}

	@Override
	@SuppressWarnings({ "rawtypes" })
	public Object lPop(final byte[] key) {
		return redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer serializerObj = redisTemplate.getDefaultSerializer();
				byte[] value = connection.lPop(key);
				if (value == null) {
					return null;
				}
				return serializerObj.deserialize(value);
			}
		});
	}

	@Override
	@SuppressWarnings({ "rawtypes" })
	public Object lPop(final String key) {
		return redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				RedisSerializer serializerObj = redisTemplate.getDefaultSerializer();
				byte[] value = connection.lPop(serializer.serialize(key));
				if(value == null) {
					return null;
				}
				return serializerObj.deserialize(value);
			}
		});
	}

	@Override
	public Long lLen(final byte[] key) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.lLen(key);
			}
		});
	}

	@Override
	public Long lLen(final String key) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				return connection.lLen(serializer.serialize(key));
			}
		});
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public List<Object> lRange(final byte[] key) {
		return redisTemplate.execute(new RedisCallback<List<Object>>() {
			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer serializerObj = redisTemplate.getDefaultSerializer();
				long length = connection.lLen(key);
				List<byte[]> values = connection.lRange(key, 0, length);
				if (values == null) {
					return null;
				}
				List<Object> objects = new ArrayList<Object>();
				for (byte[] value : values) {
					Object object = serializerObj.deserialize(value);
					objects.add(object);
				}
				connection.del(key);
				return objects;
			}
		});
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public List<Object> lRange(final String key) {
		return redisTemplate.execute(new RedisCallback<List<Object>>() {
			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				RedisSerializer serializerObj = redisTemplate.getDefaultSerializer();
				byte[] keys = serializer.serialize(key);
				long length = connection.lLen(keys);
				List<byte[]> values = connection.lRange(keys, 0, length);
				if (values == null) {
					return null;
				}
				List<Object> objects = new ArrayList<Object>();
				for (byte[] value : values) {
					Object object = serializerObj.deserialize(value);
					objects.add(object);
				}
				connection.del(keys);
				return objects;
			}
		});
	}
	
	@Override
	@SuppressWarnings({ "rawtypes" })
	public Object get(final byte[] key) {
		return redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer serializerObj = redisTemplate.getDefaultSerializer();
				byte[] value = connection.get(key);
				if (value == null) {
					return null;
				}
				return serializerObj.deserialize(value);
			}
		});
	}
	
	@Override
	@SuppressWarnings({ "rawtypes" })
	public Object get(final String key) {
		return redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				RedisSerializer serializerObj = redisTemplate.getDefaultSerializer();
				byte[] keys = serializer.serialize(key);
				byte[] value = connection.get(keys);
				if (value == null) {
					return null;
				}
				return serializerObj.deserialize(value);
			}
		});
	}

	@Override
	public Boolean exists(final byte[] key) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.exists(key);
			}
		});
	}
	
	@Override
	public Boolean exists(final String key) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
            	RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                return connection.exists(serializer.serialize(key));
            }
        });
	}

	@Override
	public Object type(final byte[] key) {
		return redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.type(key);
			}
		});
	}
	
	@Override
	public Object type(final String key) {
		return redisTemplate.execute(new RedisCallback<Object>() {
			@Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
            	RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                return connection.type(serializer.serialize(key));
            }
        });
	}
	
	@Override
	public String flushDB() {
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "OK";
            }
        });
	}

	@Override
	public Long dbSize() {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
	}

	@Override
	public String ping() {
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ping();
            }
        });
	}

	@Override
	public Boolean expire(final byte[] key, final Long seconds) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.expire(key, seconds);
			}
		});
	}
	
	@Override
	public Boolean expire(final String key, final Long seconds) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				return connection.expire(serializer.serialize(key), seconds);
			}
		});
	}

	@Override
	public Boolean expireAt(final byte[] key, final Long unixTime) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.expireAt(key, unixTime);
			}
		});
	}
	
	@Override
	public Boolean expireAt(final String key, final Long unixTime) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				return connection.expireAt(serializer.serialize(key), unixTime);
			}
		});
	}
}