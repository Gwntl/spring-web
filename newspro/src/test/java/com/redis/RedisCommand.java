package com.redis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.mine.aplt.util.CommonUtils;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;

public class RedisCommand {
	private static JedisPool pool = null;
	private static String ADDRESS = "localhost";
	private static int PORT = 6379;
	private static int TIMEOUT = 1000 * 1;
	static{
		JedisPoolConfig config = new JedisPoolConfig();
		//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		config.setBlockWhenExhausted(false);
		//设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
		config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
		//是否启用pool的jmx管理功能, 默认true
		config.setJmxEnabled(true);
		//最大空闲连接数, 默认8个 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例
		config.setMaxIdle(8);
		//最大连接数, 默认8个
		config.setMaxTotal(200);
		//表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException
		config.setMaxWaitMillis(1000 * 10);
		//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
		config.setTestOnBorrow(true);
		pool = new JedisPool(config, ADDRESS, PORT, TIMEOUT);
	}
	public synchronized static Jedis getJedis(){
		if(pool != null){
			return pool.getResource();
		}
		return null;
	}
	
	public static void main(String[] args) {
//		redisString();
//		redisList();
//		redisMap();
//		redisSet();
//		redisZSet();
		Node node = new Node();
		node.value = 1;
		Node node1 = new Node();
		node1.value = 2;
		node.next = node1;
		node1.pre = node;
		Node node2 = new Node();
		node2.value = 3;
		node2.pre = node1;
		node1.next = node2;
		
		System.out.println(">>>> : " + node2.toString());
		System.out.println(">>>> : " + node1.toString());
		System.out.println(">>>> : " + node.toString());
		Node pre = node2.pre;
		node2.pre = pre = pre.pre;
		System.out.println(pre.toString());
		System.out.println(node2.toString());
		System.out.println(node1.toString());
		System.out.println(node.toString());
		
		
		int a = 0;
		int b = 1;
		int c = 2;
		a = b =c;
		System.out.println(a + ", " + b + ", " + c);
	}
	
	public static void redisString(){
		Jedis jedis = getJedis();
		if(jedis.exists("test_key")) jedis.del("test_key");
		if(jedis.exists("test_integer")) jedis.del("test_integer");
		System.out.println(jedis.getClient().getPort());
		jedis.set("test_key", "demo001");
		System.out.println(jedis.get("test_key"));
		System.out.println(jedis.exists("test_key") ? jedis.del("test_key") : "FAIL");
		System.out.println(jedis.get("test_key"));
		jedis.set("test_integer", "1");
		System.out.println(jedis.get("test_integer"));
		jedis.incr("test_integer");
		System.out.println(jedis.get("test_integer"));
		jedis.incrBy("test_integer", 10L);
		System.out.println(jedis.get("test_integer"));
		if(jedis.exists("test_key")) jedis.del("test_key");
		if(jedis.exists("test_integer")) jedis.del("test_integer");
	}
	
	public static void redisList(){
		Jedis jedis = getJedis();
		if(jedis.exists("test_list")) jedis.del("test_list");
		jedis.lpush("test_list", "demo001");
		jedis.lpush("test_list", "demo002");
		jedis.lpush("test_list", "demo003");
		System.out.println(Arrays.toString(jedis.lrange("test_list", 0, 10).toArray()));
		jedis.rpush("test_list", "demo004");
		jedis.rpush("test_list", "demo005");
		jedis.rpush("test_list", "demo006");
		System.out.println(Arrays.toString(jedis.lrange("test_list", 0, 10).toArray()) + ", " + jedis.llen("test_list"));
		System.out.println(jedis.lpop("test_list"));
		System.out.println(jedis.rpop("test_list"));	
		System.out.println(Arrays.toString(jedis.lrange("test_list", 0, 10).toArray()) + ", " + jedis.llen("test_list"));
		System.out.println(jedis.lset("test_list", 3, "demo007"));
		System.out.println(Arrays.toString(jedis.lrange("test_list", 0, 10).toArray()) + ", " + jedis.llen("test_list"));
		System.out.println(jedis.lindex("test_list", 3));
		jedis.linsert("test_list", LIST_POSITION.BEFORE, "demo007", "demo006");
		System.out.println(Arrays.toString(jedis.lrange("test_list", 0, 10).toArray()) + ", " + jedis.llen("test_list"));
		if(jedis.exists("test_list")) jedis.del("test_list");
	}
	
	public static void redisMap(){
		Jedis jedis = getJedis();
		if(jedis.exists("test_map")) jedis.del("test_map");
		jedis.hset("test_map", "username", "userdemo1");
		jedis.hset("test_map", "password", "passdemo1");
		jedis.hset("test_map", "point", "pointdemo1");
		System.out.println(jedis.hget("test_map", "username"));
		System.out.println(jedis.hmget("test_map", new String[]{"username", "password", "point"}));
		System.out.println(Arrays.toString(jedis.hkeys("test_map").toArray()));
		System.out.println(Arrays.toString(jedis.hvals("test_map").toArray()));
		System.out.println(jedis.hexists("test_map", "username"));
		System.out.println(jedis.hexists("test_map", "sss"));
		System.out.println(CommonUtils.toString(jedis.hgetAll("test_map")));
		System.out.println(jedis.hlen("test_map"));
		jedis.hset("test_map", "username", "usredemo2");
		System.out.println(CommonUtils.toString(jedis.hgetAll("test_map")));
		if(jedis.exists("test_map")) jedis.del("test_map");
	}
	
	public static void redisSet(){
		Jedis jedis = getJedis();
		if(jedis.exists("test_set")) jedis.del("test_set");
		if(jedis.exists("test_set1")) jedis.del("test_set1");
		jedis.sadd("test_set", new String[]{"a", "b", "c"});
		System.out.println(jedis.scard("test_set"));
		System.out.println(Arrays.toString(jedis.smembers("test_set").toArray()));
		jedis.sadd("test_set", new String[]{"a", "d"});
		System.out.println(Arrays.toString(jedis.smembers("test_set").toArray()));
		jedis.srem("test_set", "a");
		System.out.println(jedis.srandmember("test_set"));
		System.out.println(jedis.spop("test_set"));
		System.out.println(Arrays.toString(jedis.smembers("test_set").toArray()));
		jedis.sadd("test_set1", new String[]{"a", "b", "r", "f"});
		System.out.println(Arrays.toString(jedis.sinter("test_set", "test_set1").toArray()));
		if(jedis.exists("test_set")) jedis.del("test_set");
		if(jedis.exists("test_set1")) jedis.del("test_set1");
	}
	
	public static void redisZSet(){
		Jedis jedis = getJedis();
		if(jedis.exists("test_zset")) jedis.del("test_zset");
		Map<String, Double> map = new HashMap<>();
		map.put("one", 1.00);
		map.put("two", 2.00);
		map.put("three", 3.00);
		jedis.zadd("test_zset", map);
		System.out.println(toString(jedis.zrangeWithScores("test_zset", 0, 10)));
		System.out.println(Arrays.toString(jedis.zrevrange("test_zset", 0, 10).toArray()));
		System.out.println(jedis.zrevrank("test_zset", "three"));
		if(jedis.exists("test_zset")) jedis.del("test_zset");
	}
	
	public static String toString(Set<Tuple> tuples){
		String str = "";
		Iterator<Tuple> iterator = tuples.iterator();
		while(iterator.hasNext()){
			Tuple tuple =  iterator.next();
			str += tuple.toString();
			if(iterator.hasNext()){
				str += ", ";
			}
		}
		return str;
	}
	
	static class Node{
		Node pre;
		Node next;
		int value;
		@Override
		public String toString() {
			return "pre : " + (pre != null ? pre.toString() : null) + ", value :" + value;
		}
	}
}
