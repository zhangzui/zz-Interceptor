package com.zz.interceptor.schedul;

import java.util.concurrent.*;

/**
 * Created by zhangzuizui on 2018/1/24.
 */
public class ScheduledExe {
    private final ScheduledExecutorService scheduledExecutor;
    private final ExecutorService executor = Executors.newCachedThreadPool(new MyThreadFactory());

    public ScheduledExe() {
        this.scheduledExecutor = Executors.newScheduledThreadPool(1,new MyThreadFactory());
        this.scheduledExecutor.scheduleWithFixedDelay(new FetchBlockingThread("sasa", new ConcurrentHashMap<String, String>()), 3L, 5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        ScheduledExe exe = new ScheduledExe();
    }
}
