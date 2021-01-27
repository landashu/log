package com.wt.cloud.handle.execute;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giant.cloud.connection.RedisConnection;
import com.giant.cloud.entity.LogMsgEntity;
import com.giant.cloud.util.LogCacheUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author big uncle
 * @date 2021/1/26 14:27
 * @module
 **/
public class RedisExecuteHandle {

    private static final Log log = LogFactory.getLog(RedisExecuteHandle.class);
    private RedisConnection redisConnection;
    private ObjectMapper objectMapper = new ObjectMapper();

    public RedisExecuteHandle(RedisConnection redisConnection){
        this.redisConnection = redisConnection;
    }


    public void exec(){
        while(true) {
            Jedis jedis = null;
            try {
                Thread.sleep(redisConnection.getRedisEntity().getDelay());
                jedis = redisConnection.getJedis();
                List<ILoggingEvent> logs = LogCacheUtil.getAndClear();
                if(logs == null || logs.isEmpty()){
                    continue;
                }
                String[] json = new String[logs.size()];
                LogMsgEntity logMsgEntity = new LogMsgEntity();
                for (int i=0;i<logs.size();i++){
                    logMsgEntity = LogMsgEntity.toLogEntity(logMsgEntity,logs.get(i));
                    json[i] = objectMapper.writeValueAsString(logMsgEntity);
                    logMsgEntity = LogMsgEntity.clear(logMsgEntity);
                }
                logMsgEntity = null;
                jedis.lpush(redisConnection.getRedisEntity().getKey(),json);
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }finally {
                if(jedis!=null) {
                    jedis.close();
                }
            }
        }
    }



}
