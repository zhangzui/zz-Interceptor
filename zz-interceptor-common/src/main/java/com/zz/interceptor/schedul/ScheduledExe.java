package com.zz.interceptor.schedul;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzuizui on 2018/1/24.
 */
public class ScheduledExe {
    private final ScheduledExecutorService scheduledExecutor;
    private final ExecutorService executor = Executors.newCachedThreadPool(new MyThreadFactory());

    public ScheduledExe(ScheduledExecutorService scheduledExecutor) {
        this.scheduledExecutor = Executors.newSingleThreadScheduledExecutor(new MyThreadFactory());
        this.scheduledExecutor.scheduleWithFixedDelay(new FetchBlockingThread(null), 3L, 5, TimeUnit.SECONDS);
    }
}
