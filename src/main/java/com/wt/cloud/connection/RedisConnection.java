package com.wt.cloud.connection;

import com.giant.cloud.entity.LogEntity;
import com.giant.cloud.entity.RedisEntity;
import com.giant.cloud.util.LogYmlUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author big uncle
 * @date 2021/1/26 11:00
 * @module
 **/
public class RedisConnection {

    private JedisPool pool = null;
    private LogEntity logEntity = null;
    private Integer num = 5;

    public void init(){
        logEntity = LogYmlUtil.getLogEntity();
        RedisEntity redisEntity = logEntity.getRedis().check();
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(num);
        genericObjectPoolConfig.setMaxIdle(num);
        genericObjectPoolConfig.setMinIdle(1);
        genericObjectPoolConfig.setMaxWaitMillis(3000);
        pool = new JedisPool(genericObjectPoolConfig,redisEntity.getHost(), redisEntity.getPort(), redisEntity.getTimeout(), redisEntity.getPassword(),redisEntity.getDatabase());
    }

    public void destroy(){
        pool.destroy();
    }

    public RedisEntity getRedisEntity() {
        return logEntity.getRedis();
    }

    public LogEntity getLogEntity(){
        return logEntity;
    }

    public Jedis getJedis(){
        int count = 1;
        Jedis jedis = pool.getResource();
        while(jedis ==null && !jedis.isConnected()) {
            if(count >= num){
                throw new RuntimeException("jedis is null or closed");
            }
            jedis = pool.getResource();
            count++;
        }
        return jedis;
    }

}
