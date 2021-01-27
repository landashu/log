package com.wt.cloud.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.giant.cloud.handle.MysqlHandle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author big uncle
 * @date 2020/12/18 14:20
 * @module
 **/
public class LogCacheUtil {

    private static final Log log = LogFactory.getLog(MysqlHandle.class);

    private static List<ILoggingEvent> logArray = new ArrayList<ILoggingEvent>(10);

    private static ReentrantLock reentrantLock = new ReentrantLock();

    public static void add(ILoggingEvent str) {
        reentrantLock.lock();
        try {
            logArray.add(str);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
    }

    public static List<ILoggingEvent> getAndClear(){
        reentrantLock.lock();
        try {
            List<ILoggingEvent> logs = new ArrayList<>();
            logs.addAll(logArray);
            logArray.clear();
            return logs;
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
        return null;
    }




}
