package com.bac.bacbackend;
import redis.clients.jedis.UnifiedJedis;

public class RedisTest {
    public static void main(String[] args) {
        UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");

        String res1 = jedis.set("bike:1", "Deimos");
        System.out.println(res1); // OK

        String res2 = jedis.get("bike:1");
        System.out.println(res2); // Deimos

        jedis.close();
    }
}
