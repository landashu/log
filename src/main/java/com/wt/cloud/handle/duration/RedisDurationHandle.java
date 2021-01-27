package com.wt.cloud.handle.duration;

import com.giant.cloud.connection.RedisConnection;
import com.giant.cloud.handle.execute.RedisExecuteHandle;

/**
 * @author big uncle
 * @date 2021/1/26 10:49
 * @module
 **/
public class RedisDurationHandle extends RedisExecuteHandle {

    private RedisConnection redisConnection;

    public RedisDurationHandle(RedisConnection redisConnection) {
        super(redisConnection);
        this.redisConnection = redisConnection;
    }

    public void execute(){
        super.exec();
    }


}
