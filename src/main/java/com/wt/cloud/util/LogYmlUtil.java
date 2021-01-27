package com.wt.cloud.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giant.cloud.entity.LogEntity;
import com.giant.cloud.entity.MysqlEntity;
import com.giant.cloud.entity.RedisEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;

/**
 * @author big uncle
 * @date 2020/12/18 9:17
 * @module
 **/
public class LogYmlUtil {

    public static String filePrefix = "application";

    public static String fileSuffix = ".yml";

    private static String defaultFile = filePrefix+fileSuffix;

    private static final Log log = LogFactory.getLog(LogYmlUtil.class);

    /**
     * 获取配置文件内容
    **/
    private static RedisEntity getRedisEntity(String ymlFile) throws IOException {
        Yaml yaml = new Yaml();
        Resource resource = new ClassPathResource(ymlFile);
        Map<String, Object> ymlText = yaml.load(resource.getInputStream());
        Map<String,Object> log = (Map<String, Object>) ymlText.get("log");
        Map<String,Object> redis = (Map<String,Object>) log.get("redis");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(redis, RedisEntity.class);
    }

    /**
     * 获取配置文件内容
     **/
    private static MysqlEntity getMysqlEntity(String ymlFile) throws IOException {
        Yaml yaml = new Yaml();
        Resource resource = new ClassPathResource(ymlFile);
        Map<String, Object> ymlText = yaml.load(resource.getInputStream());
        Map<String,Object> log = (Map<String, Object>) ymlText.get("log");
        Map<String,Object> mysql = (Map<String,Object>) log.get("mysql");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(mysql, MysqlEntity.class);
    }

    private static LogEntity getLogEntity(String ymlFile) throws IOException {
        Yaml yaml = new Yaml();
        Resource resource = new ClassPathResource(ymlFile);
        Map<String, Object> ymlText = yaml.load(resource.getInputStream());
        Map<String,Object> log = (Map<String, Object>) ymlText.get("log");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(log, LogEntity.class);
    }

    /**
     * 获取环境
    **/
    private static String getEnvInfo() {
        try {
            Yaml yaml = new Yaml();
            Resource resource = new ClassPathResource(defaultFile);
            Map<String, Object> map = yaml.load(resource.getInputStream());
            Map<String, Object> spring = (Map<String, Object>) map.get("spring");
            Map<String, Object> profiles = (Map<String, Object>) spring.get("profiles");
            String active = (String) profiles.get("active");
            if (active == null || active.trim().equals("default")) {
                return defaultFile;
            }
            return filePrefix+"-"+active+fileSuffix;
        }catch (Exception e){
            log.debug("not find file "+defaultFile);
        }
        return defaultFile;
    }

    public static LogEntity getLogEntity(){
        LogEntity logEntity = null;
        try {
            logEntity = getLogEntity(getEnvInfo());
        }catch (Exception e){
            LogNacosUtil logNacosUtil = new LogNacosUtil();
            logEntity = logNacosUtil.getLogEntity();
            if(logEntity == null){
                e.printStackTrace();
            }
        }
        return logEntity;
    }

}
