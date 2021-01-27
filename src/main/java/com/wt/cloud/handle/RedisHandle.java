package com.wt.cloud.handle;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.giant.cloud.connection.RedisConnection;
import com.giant.cloud.handle.duration.RedisDurationHandle;
import com.giant.cloud.util.LogCacheUtil;
import com.giant.cloud.util.LogThreadPoolUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author big uncle
 * @date 2020/12/17 18:37
 * @module
 **/
public class RedisHandle implements LogHandle {

    private static final Log log = LogFactory.getLog(RedisHandle.class);
    private RedisConnection redisConnection = new RedisConnection();


    @Override
    public void init() {
        try {
            redisConnection.init();
        }catch(Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        redisConnection.destroy();
    }

    @Override
    public void append(ILoggingEvent iLoggingEvent, String str) {
        LogCacheUtil.add(iLoggingEvent);
    }

    @Override
    public void execute() {
        RedisDurationHandle redisDurationHandle = new RedisDurationHandle(redisConnection);
        LogThreadPoolUtil logThreadPoolUtil = new LogThreadPoolUtil();
        logThreadPoolUtil.getThreadPool(1);
        logThreadPoolUtil.getThreadPoolExecutor().execute(() -> redisDurationHandle.execute());
    }


}
