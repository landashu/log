package com.wt.cloud.entity;

/**
 * @author big uncle
 * @date 2021/1/7 16:28
 * @module
 **/
public class NacosEntity {

    public static final String GROUP = "spring.cloud.nacos.config.group";
    public static final String FILEEXTENSION = "spring.cloud.nacos.config.file-extension";
    public static final String APPLICATIONNAME = "spring.application.name";
    public static final String ACTIVE = "spring.profiles.active";
    public static final String CONFIGSERVERADDR = "spring.cloud.nacos.config.server-addr";
    public static final String DISCOVERYSERVERADDR = "spring.cloud.nacos.discovery.server-addr";
    public static final String NAMESPACE = "spring.cloud.nacos.config.namespace";


    private String group;

    private String fileExtension;

    private String applicationName;

    private String active;

    private String configServerAddr;

    private String discoveryServerAddr;

    private String namespace;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getConfigServerAddr() {
        return configServerAddr;
    }

    public void setConfigServerAddr(String configServerAddr) {
        this.configServerAddr = configServerAddr;
    }

    public String getDiscoveryServerAddr() {
        return discoveryServerAddr;
    }

    public void setDiscoveryServerAddr(String discoveryServerAddr) {
        this.discoveryServerAddr = discoveryServerAddr;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
