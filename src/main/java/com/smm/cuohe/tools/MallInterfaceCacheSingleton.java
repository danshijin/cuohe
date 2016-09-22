package com.smm.cuohe.tools;

import redis.clients.jedis.Jedis;

/**
 * @author  zhaoyutao
 * @version 2015年9月17日 下午5:58:55
 * 类说明  一个商城接口，如果它的返回值比较固定，一般不会有变动，就可以放入缓存中
 */

public class MallInterfaceCacheSingleton {

	private MallInterfaceCacheSingleton(){}
	
	private static Jedis instance = MybatisRedisCache.createReids();
	
	/**根据key获取value。如果对应的key不存在，返回null。如果key对应的value不是string，报错。 
	 * @param key
	 * @return 键对应的值
	 */
	public static String get(String key){
		return instance.get(key);
	}
	
	/** 添加key:value键值对，value值不能大于1073741824 bytes (1 GB); 
	 * @param key
	 * @param value
	 * @return 返回的状态值
	 */
	public static String put(String key, String value){
		return instance.set(key, value);
	}
	
	
	/** 对一个特定的key设置一个超时时间，超时时间过后，服务器会自动把这个键值对删除，带有超时时间的key在redis术语中被认为是不稳定的。 
	 * <p>
	 * Voltile keys are stored on disk like the other keys, the timeout is persistent too like all the other aspects of the dataset. Saving a dataset containing expires and stopping the server does not stop the flow of time as Redis stores on disk the time when the key will no longer be available as Unix time, and not the remaining seconds. 
	 * @param key
	 * @param seconds
	 * @return Integer reply, specifically: 1: the timeout was set. 0: the timeout was not set since the key already has an associated timeout (this may happen only in Redis versions < 2.1.3, Redis >= 2.1.3 will happily update the timeout), or the key does not exist.
	 */
	public static Long setExpire(String key, int seconds){
		return instance.expire(key, seconds);
	}
	
	
}
