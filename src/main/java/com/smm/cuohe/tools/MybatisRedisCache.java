package com.smm.cuohe.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class MybatisRedisCache implements Cache {

	private static Logger logger = Logger.getLogger(MybatisRedisCache.class);
	private Jedis redisClient = createReids();
	/** The ReadWriteLock. */
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	private String id;

	public MybatisRedisCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>MybatisRedisCache:id=" + id);
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public int getSize() {

		return Integer.valueOf(redisClient.dbSize().toString());
	}

	@Override
	public void putObject(Object key, Object value) {
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>putObject:" + key + "=" + value);
		redisClient.set(SerializeUtil.serialize(key.toString()),
				SerializeUtil.serialize(value));
	}

	@Override
	public Object getObject(Object key) {
		Object value = SerializeUtil.unserialize(redisClient.get(SerializeUtil
				.serialize(key.toString())));
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>getObject:" + key + "=" + value);
		return value;
	}

	@Override
	public Object removeObject(Object key) {
		return redisClient.expire(SerializeUtil.serialize(key.toString()), 0);
	}
	
	public Object setExpire(Object key, int seconds) {
		return redisClient.expire(SerializeUtil.serialize(key.toString()), 0);
	}

	@Override
	public void clear() {
		redisClient.flushDB();
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}
	
	protected static Jedis createReids() {
		FileInputStream fis = null;
		JedisPool pool = null;
		try {
			String path = System.getProperty("smm.cuohe") + "/WEB-INF/classes/config.properties";

			Properties pros = new Properties();
			fis = new FileInputStream(path);
			pros.load(fis);
			String ip = pros.getProperty("jedis.ip");
			int port = Integer.parseInt(pros.getProperty("jedis.port"));

			pool = new JedisPool(ip, port);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(pool == null){
				pool = new JedisPool("127.0.0.1", 6379);
			}
		}
		Jedis jedis = pool.getResource();
		jedis.flushDB(); //删除当前使用数据库的缓存
		return jedis;
	}
}