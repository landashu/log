package com.wt.cloud.entity;

/**
 * @author big uncle
 * @date 2021/1/26 10:47
 * @module
 **/
public class LogEntity {

    private Integer duration = 7;

    private RedisEntity redis;

    private MysqlEntity mysql;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public RedisEntity getRedis() {
        return redis;
    }

    public void setRedis(RedisEntity redis) {
        this.redis = redis;
    }

    public MysqlEntity getMysql() {
        return mysql;
    }

    public void setMysql(MysqlEntity mysql) {
        this.mysql = mysql;
    }
}
