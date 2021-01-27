package com.wt.cloud.util;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.client.config.NacosConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giant.cloud.entity.LogEntity;
import com.giant.cloud.entity.NacosEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * @author big uncle
 * @date 2021/1/7 16:12
 * @module
 **/
public class LogNacosUtil {

    private static String filePrefix = "bootstrap";

    private static String fileSuffix = ".properties";

    private static String defaultFile = filePrefix+fileSuffix;

    private static NacosEntity nacosEntity = new NacosEntity();

    private Properties properties = new Properties();

    private static final Log log = LogFactory.getLog(LogNacosUtil.class);

    private String getEnvInfo() {
        try {
            Resource resource = new ClassPathResource(defaultFile);
            properties.load(resource.getInputStream());
            String active = properties.getProperty(NacosEntity.ACTIVE);
            if (active == null || active.trim().equals("default")) {
                return defaultFile;
            }
            properties.clear();
            return filePrefix+"-"+active+fileSuffix;
        }catch (Exception e){
            log.debug("not find file "+defaultFile);
        }
        return defaultFile;
    }

    private NacosConfigService getNacosEntity() throws Exception {
        String file = getEnvInfo();
        Arrays.asList(defaultFile,file).forEach(i -> {
            try {
                Resource resource = new ClassPathResource(i);
                properties.load(resource.getInputStream());
                nacosEntity.setActive( properties.getProperty(NacosEntity.ACTIVE));
                nacosEntity.setApplicationName(properties.getProperty(NacosEntity.APPLICATIONNAME));
                nacosEntity.setGroup(properties.getProperty(NacosEntity.GROUP));
                nacosEntity.setFileExtension(properties.getProperty(NacosEntity.FILEEXTENSION));
                nacosEntity.setNamespace(properties.getProperty(NacosEntity.NAMESPACE));
                nacosEntity.setDiscoveryServerAddr(properties.getProperty(NacosEntity.DISCOVERYSERVERADDR));
                nacosEntity.setConfigServerAddr(properties.getProperty(NacosEntity.CONFIGSERVERADDR));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        properties.clear();
        properties.put(PropertyKeyConst.SERVER_ADDR, nacosEntity.getConfigServerAddr());
        properties.put(PropertyKeyConst.NAMESPACE,nacosEntity.getNamespace());
        NacosConfigService configService = (NacosConfigService) NacosFactory.createConfigService(properties);
        properties.clear();
        return configService;
    }


    public LogEntity getLogEntity(){
        try {
            NacosConfigService configService = getNacosEntity();
            String dataId = nacosEntity.getApplicationName()+"-"+nacosEntity.getActive()+"."+nacosEntity.getFileExtension();
            String source = configService.getConfig(dataId,nacosEntity.getGroup(),5000);
            Yaml yaml = new Yaml();
            Map<String,Object> map = yaml.load(source);
            Map<String,Object> log = (Map<String, Object>) map.get("log");
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(log, LogEntity.class);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
