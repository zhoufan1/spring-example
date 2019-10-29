package com.example.eureka;

import java.util.Arrays;
import java.util.Objects;

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



        int element = 3;
        int index = 0;
        Integer[] elementData = new Integer[1];
        elementData[0] = 8;
        long size = Arrays.stream(elementData).filter(Objects::nonNull).count();
        int newCapacity = elementData.length + (elementData.length >> 1);
        System.out.println(elementData.length +"\t" + newCapacity);
        elementData=Arrays.copyOf(elementData, elementData.length+1);
        System.arraycopy(elementData, index, elementData, index + 1,
                (int) (size-index));
        System.out.println(elementData.length-index);
        elementData[index] = element;
        System.out.println(Arrays.toString(elementData));
//        System.out.println(0x00);
      /*  ArrayList<Integer> el = new ArrayList<>();
        el.add(9);
        el.add(0, 8);
        System.out.println(el.toString());*/
    }

}
