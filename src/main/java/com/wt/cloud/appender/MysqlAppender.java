package com.wt.cloud.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.giant.cloud.handle.LogHandle;
import com.giant.cloud.handle.MysqlHandle;
import lombok.Data;

/**
 * @author big uncle
 * @date 2020/12/17 14:51
 * @module
 **/
@Data
public class MysqlAppender extends UnsynchronizedAppenderBase<ILoggingEvent>{

    Layout<ILoggingEvent> layout;

    LogHandle logHandle = new MysqlHandle();

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        if (iLoggingEvent == null || !isStarted()){
            return;
        }
        if(logHandle == null){
            throw new RuntimeException("redis connection error");
        }
        // 此处自定义实现输出
        // 获取输出值：event.getFormattedMessage()
        // 格式化输出
        logHandle.append(iLoggingEvent,layout.doLayout(iLoggingEvent));
    }

    @Override
    public void start(){
        // 这里可以做些初始化判断 比如layout不能为null ,
        if(layout == null) {
            addWarn("Layout was not defined");
        }
        // 初始化 redis
        // 或者写入数据库 或者redis时 初始化连接等等
        logHandle.init();
        logHandle.execute();
        super.start();
    }

    @Override
    public void stop()
    {
        // 释放相关资源，如数据库连接，redis线程池等等
        if(!isStarted()) {
            return;
        }
        logHandle.destroy();
        super.stop();
    }


}
