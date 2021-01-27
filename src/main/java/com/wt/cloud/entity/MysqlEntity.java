package com.wt.cloud.entity;

/**
 * @author big uncle
 * @date 2020/12/17 18:40
 * @module
 **/
public class MysqlEntity {

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
     * 延时 单位 ms
    **/
    private Integer delay = 3000;

    private String table;

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

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

    public MysqlEntity check(){
        if(this.getUrl() == null || this.getUrl().trim() == ""){
            throw new RuntimeException("redis.mysql.url is null");
        }
        if(this.getUsername() == null || this.getUsername().trim() == ""){
            throw new RuntimeException("redis.mysql.username is null");
        }
        if(this.getPassword() == null || this.getPassword().trim() == ""){
            throw new RuntimeException("redis.mysql.password is null");
        }
        if(this.getTable() == null || this.getTable().trim() == ""){
            throw new RuntimeException("redis.mysql.table is null");
        }
        if(this.getDelay() == null){
            throw new RuntimeException("redis.mysql.delay is null");
        }
        return this;
    }


}
