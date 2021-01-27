package com.wt.cloud.entity;

/**
 * @author big uncle
 * @date 2020/12/17 18:40
 * @module
 **/
public class RedisEntity {

    private String host;

    private String password;

    private Integer database;

    private Integer port;
    /**
     * 超时时间 单位 ms
    **/
    private Integer timeout;
    /**
     * 存库延时 单位 ms
    **/
    private Integer delay = 3000;
    /**
     * redis key
    **/
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }


    public RedisEntity check(){
        if(this.getHost() == null || this.getHost().trim() == ""){
            throw new RuntimeException("redis.log.host is null");
        }
        if(this.getPort() == null){
            throw new RuntimeException("redis.log.port is null");
        }
        if(this.getTimeout() == null){
            throw new RuntimeException("redis.log.timeout is null");
        }
        if(this.getKey() == null|| this.getKey().trim() == ""){
            throw new RuntimeException("redis.log.key is null");
        }
        if(this.getDelay() == null){
            throw new RuntimeException("redis.log.delay is null");
        }
        return this;
    }
}
