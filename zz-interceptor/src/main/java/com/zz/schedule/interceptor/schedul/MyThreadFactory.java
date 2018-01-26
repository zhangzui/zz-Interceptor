package com.zz.schedule.interceptor.schedul;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhangzuizui on 2018/1/24.
 */
public class MyThreadFactory implements ThreadFactory {
    private static final Logger logger = LoggerFactory.getLogger(MyThreadFactory.class);
    private static AtomicInteger thread_counter = new AtomicInteger();

    public MyThreadFactory() {
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        final String threadName = "conf_thread_" + thread_counter.getAndIncrement();
        thread.setName(threadName);
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                MyThreadFactory.logger.error(threadName, e);
            }
        });
        return thread;
    }
}
