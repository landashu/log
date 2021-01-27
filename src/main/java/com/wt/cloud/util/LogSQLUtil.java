package com.wt.cloud.util;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * @author big uncle
 * @date 2020/12/22 9:19
 * @module
 **/
public class LogSQLUtil {

    public static String getLogTableDDL() throws IOException {
        Resource resource = new ClassPathResource("LOG_TABLE_DDL.sql");
        return IOUtils.toString(resource.getInputStream()).replaceAll("\r|\n", " ");
    }

    public static String getLogClusterDDL() throws IOException {
        Resource resource = new ClassPathResource("LOG_CLUSTER_DDL.sql");
        return IOUtils.toString(resource.getInputStream()).replaceAll("\r|\n", " ");
    }


}
