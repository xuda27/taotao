package cn.eden.taotao.rest.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * 使用redis客户端jedis 测试
 * 
 * @author Eden
 *
 */
public class JedisTest {

	/**
	 * 创建jedis对象实现<br>
	 * 每次调用都要创建redis对象，性能差
	 */
	@Test
	public void testJedisSingle() {
		// 创建一个jedis的对象
		Jedis jedis = new Jedis("123.207.27.28", 6379);
		// 调用jedis对象的方法，方法名称和redis的命令一致
		jedis.set("key0", "jedis test");
		String value = jedis.get("key0");
		System.out.println(value);
		// 关闭jedis
		jedis.close();
	}

	/**
	 * 使用连接池<br>
	 * 性能相对好
	 */
	@Test
	public void testJedisPool() {
		// 创建jedis连接池
		JedisPool pool = new JedisPool("123.207.27.28", 6379);
		// 从连接池中获得jedis对象
		Jedis jedis = pool.getResource();
		System.out.println(jedis.get("key0"));
		// 关闭jedis对象和连接池对象
		jedis.close();
		pool.close();

	}

	/**
	 * 集群版jedis测试
	 */
	@Test
	public void testJedisCluster() {
		JedisCluster cluster = null;
		try {
			// 创建节点的集合
			Set<HostAndPort> nodes = new HashSet<HostAndPort>();
			// 将节点添加到set集合对象中
			nodes.add(new HostAndPort("123.207.27.28", 7001));
			nodes.add(new HostAndPort("123.207.27.28", 7002));
			nodes.add(new HostAndPort("123.207.27.28", 7003));
			nodes.add(new HostAndPort("123.207.27.28", 7004));
			nodes.add(new HostAndPort("123.207.27.28", 7005));
			nodes.add(new HostAndPort("123.207.27.28", 7006));
			cluster = new JedisCluster(nodes);
			cluster.set("key1", "testCluster");
			System.out.println(cluster.get("key1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			cluster.close();
		}

	}

	@Test
	public void JedisPoolTest() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		JedisPool pool = (JedisPool) ac.getBean("jedisPool");
		// 从连接池中获得jedis对象
		Jedis jedis = pool.getResource();
		System.out.println(jedis.get("key0"));
		// 关闭jedis对象和连接池对象
		jedis.close();
		pool.close();
	}
}
