package com.wt.cloud.handle;

import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * @author big uncle
 * @date 2020/12/17 16:32
 **/
public interface LogHandle {

    void init();

    void destroy();

    void append(ILoggingEvent iLoggingEvent, String str);

    void execute();

}
