package com.wt.cloud.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author big uncle
 * @date 2020/12/18 9:11
 * @module
 **/
@ConfigurationProperties(prefix = "log.redis")
public class RedisProperties {


    private String host;
    /**
     * 密码
    **/
    private String password;
    /**
     * 库
    **/
    private Integer database;
    /**
     * 端口
    **/
    private Integer port;
    /**
     * 超时时间 ms
    **/
    private Integer timeout;
    /**
     * 多久save一次 单位 ms
    **/
    private Integer delay;
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
}
