package com.wt.cloud.entity;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.giant.cloud.util.LogDateUtil;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author big uncle
 * @date 2020/12/18 17:15
 * @module
 **/
public class LogMsgEntity {

    private String logTime;

    private String thread;

    private String logLevel;

    private String packagePath;

    private String msg;

    public String getThread() {
        return thread;
    }

    public LogMsgEntity setThread(String thread) {
        this.thread = thread;
        return this;
    }

    public String getLogTime() {
        return logTime;
    }

    public LogMsgEntity setLogTime(String logTime) {
        this.logTime = logTime;
        return this;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public LogMsgEntity setLogLevel(String logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public LogMsgEntity setPackagePath(String packagePath) {
        this.packagePath = packagePath;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public LogMsgEntity setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public static LogMsgEntity toLogEntity(LogMsgEntity logMsgEntity, ILoggingEvent iLoggingEvent){
        long timeStamp = iLoggingEvent.getTimeStamp();
        String time = LogDateUtil.toFormatStr(timeStamp);
        return logMsgEntity.setLogLevel(iLoggingEvent.getLevel().toString())
                .setMsg(StringEscapeUtils.escapeSql(iLoggingEvent.getFormattedMessage())).setPackagePath(iLoggingEvent.getLoggerName())
                .setThread(iLoggingEvent.getThreadName()).setLogTime(time);
    }

    public static LogMsgEntity clear(LogMsgEntity logEntit){
        return logEntit.setLogLevel("").setLogTime("")
                .setMsg("").setPackagePath("").setThread("");
    }

    @Override
    public String toString(){
        return "('"+this.getLogTime()+"','"+this.getThread()+"','"+this.getLogLevel()+"','"+this.getPackagePath()+"','"+this.getMsg()+"')";
    }

    public static String getColumn(String table){
        return "insert into `"+table+"` (log_time,thread,log_level,package_path,msg) values ";
    }

}
