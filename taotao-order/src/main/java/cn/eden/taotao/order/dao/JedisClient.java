package cn.eden.taotao.order.dao;

public interface JedisClient {
	String get(String key);

	String set(String key, String value);

	String hget(String hkey, String key);

	long hset(String hkey, String key, String value);

	long incr(String key);

	long expire(String key, int second); // 设置过期时间

	long ttl(String key); // 查看剩余时间

	long del(String key);

	long hdel(String hkey, String key);

}
