package com.wt.cloud.handle;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.giant.cloud.connection.MysqlConnection;
import com.giant.cloud.handle.duration.MysqlDurationHandle;
import com.giant.cloud.util.LogCacheUtil;
import com.giant.cloud.util.LogThreadPoolUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author big uncle
 * @date 2020/12/17 18:37
 * @module
 **/
public class MysqlHandle implements LogHandle {

    private static final Log log = LogFactory.getLog(MysqlHandle.class);
    MysqlConnection mysqlConnection = new MysqlConnection();

    @Override
    public void init() {
        try {
            mysqlConnection.init();
            mysqlConnection.initTable();
        }catch(Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        mysqlConnection.destroy();
    }

    @Override
    public void append(ILoggingEvent iLoggingEvent, String str) {
        LogCacheUtil.add(iLoggingEvent);
    }

    @Override
    public void execute() {
        MysqlDurationHandle mysqlDurationHandle = new MysqlDurationHandle(mysqlConnection);
        LogThreadPoolUtil logThreadPoolUtil = new LogThreadPoolUtil();
        logThreadPoolUtil.getThreadPool(3);
        logThreadPoolUtil.getThreadPoolExecutor().execute(() -> mysqlDurationHandle.newTable());
        logThreadPoolUtil.getThreadPoolExecutor().execute(() -> mysqlDurationHandle.timedClear());
        logThreadPoolUtil.getThreadPoolExecutor().execute(() -> mysqlDurationHandle.execute());
    }



}
