package com.wt.cloud.handle.execute;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.giant.cloud.connection.MysqlConnection;
import com.giant.cloud.entity.LogMsgEntity;
import com.giant.cloud.util.LogCacheUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * @author big uncle
 * @date 2021/1/26 11:47
 * @module
 **/
public class MysqlExecuteHandle {

    private static final Log log = LogFactory.getLog(MysqlExecuteHandle.class);
    private MysqlConnection mysqlConnection;

    public MysqlExecuteHandle(MysqlConnection mysqlConnection){
        this.mysqlConnection = mysqlConnection;
    }

    public void exec(){
        while(true) {
            try {
                Thread.sleep(mysqlConnection.getMysqlEntity().getDelay());
                mysqlConnection.execute((con) -> {
                    try {
                        StringBuffer sb = new StringBuffer();
                        sb.append(LogMsgEntity.getColumn(mysqlConnection.getTableName()));
                        List<ILoggingEvent> logs = LogCacheUtil.getAndClear();
                        if(logs == null || logs.isEmpty()){
                            return;
                        }
                        LogMsgEntity logMsgEntity = new LogMsgEntity();
                        for (int i=0;i<logs.size();i++){
                            logMsgEntity = LogMsgEntity.toLogEntity(logMsgEntity,logs.get(i));
                            if(i+1 < logs.size()){
                                sb.append(logMsgEntity.toString()+",");
                            }else{
                                sb.append(logMsgEntity.toString());
                            }
                            logMsgEntity = LogMsgEntity.clear(logMsgEntity);
                        }
                        logMsgEntity = null;
                        sb.append(";");
                        con.execute(sb.toString());
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }



}
