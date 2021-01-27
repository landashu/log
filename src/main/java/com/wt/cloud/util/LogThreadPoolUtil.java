package com.wt.cloud.util;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author big uncle
 * @date 2021/1/26 11:39
 * @module
 **/
public class LogThreadPoolUtil {

    private ThreadPoolExecutor threadPoolExecutor;
    private Integer core = 20;
    private String defaultName = "log";

    public LogThreadPoolUtil getThreadPool(String name){
        threadPoolExecutor = new ThreadPoolExecutor(core,core,0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(core),new LogThreadFactory(name));
        return this;
    }

    public LogThreadPoolUtil getThreadPool(Integer initSize){
        threadPoolExecutor = new ThreadPoolExecutor(initSize,core,0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(core),new LogThreadFactory(defaultName));
        return this;
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    /**
     * 自定义线程池工厂
     * @author big uncle
     * @date 2020/10/19 17:27
     * @return
     **/
    static class LogThreadFactory implements ThreadFactory {

        private static final Map<String, AtomicInteger> poolNumber = new ConcurrentHashMap<>();

        private static final  Map<String,AtomicInteger> threadNumber = new ConcurrentHashMap<>();

        private final ThreadGroup threadGroup;

        public  final String namePrefix;

        LogThreadFactory(String name){
            SecurityManager s = System.getSecurityManager();
            threadGroup = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            if(name == null || "".equals(name.trim())){
                name = "pool";
            }
            if(poolNumber.get(name.trim()) == null){
                poolNumber.put(name,new AtomicInteger(1));
            }
            namePrefix = name +"-"+ poolNumber.get(name).getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            if(threadNumber.get(namePrefix) == null){
                threadNumber.put(namePrefix,new AtomicInteger(1));
            }
            Thread t = new Thread(threadGroup, r, namePrefix + threadNumber.get(namePrefix).getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

}
