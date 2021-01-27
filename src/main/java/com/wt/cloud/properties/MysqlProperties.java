package com.wt.cloud.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author big uncle
 * @date 2020/12/18 9:11
 * @module
 **/
@ConfigurationProperties(prefix = "log.mysql")
public class MysqlProperties {

    /**
     * 连接
    **/
    private String url;
    /**
     * 账户
    **/
    private String username;
    /**
     * 密码
    **/
    private String password;
    /**
     * 服务名称
    **/
    private String table;
    /**
     * 延时
    **/
    private Integer delay;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }
}
