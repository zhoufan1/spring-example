package com.example.eureka;

/**
 * Created by zzqno on 2016-9-30.
 */
public class RedisTest {


    public static void main(String[] args) {
        /*JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
        jedisPoolConfig.setMaxWaitMillis(30000L);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "c855afad0a1e47ec.m.cnhza.kvstore.aliyuncs.com",6379,30000,"WYWKypp2016test");

        Jedis jedis = jedisPool.getResource();
        Set<String> keys = jedis.keys("YPP:CANCEL:GODMATCH:LOCK:*");
        System.out.println(keys);*/

       /* JedisPool jedisPool1 = new JedisPool("redis://ypp:WYWKypp2016test@c855afad0a1e47ec.m.cnhza.kvstore.aliyuncs.com:6379/0");
        Jedis resource = jedisPool1.getResource();
        resource.set("1", "1");
        System.out.println(resource.get("1"));
*/
    }

}
