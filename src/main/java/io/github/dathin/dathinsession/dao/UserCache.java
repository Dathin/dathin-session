package io.github.dathin.dathinsession.dao;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class UserCache {

    private static final int TEN_MINUTES_IN_SECONDS = 600;

    private final JedisPool jedisPool;

    public UserCache(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String getUserToken(int userId) {
        try(Jedis jedis = jedisPool.getResource()) {
            return jedis.get(String.valueOf(userId));
        }
    }

    public void cacheUserToken(int userId, String token){
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.setex(String.valueOf(userId), TEN_MINUTES_IN_SECONDS, token);
        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    public void deleteUserToken(int userId) {
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.del(String.valueOf(userId));
        }
    }

    public void resetExpire(int userId) {
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.expire(String.valueOf(userId), TEN_MINUTES_IN_SECONDS);
        }
    }

}
