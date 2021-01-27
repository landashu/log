package com.wt.cloud.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author big uncle
 * @date 2021/1/26 10:39
 * @module
 **/
@ConfigurationProperties(prefix = "log")
public class LogProperties {

    private Integer duration = 7;

}
