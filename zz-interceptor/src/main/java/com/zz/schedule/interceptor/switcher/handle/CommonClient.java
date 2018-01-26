package com.zz.schedule.interceptor.switcher.handle;

import com.zz.schedule.interceptor.schedul.FetchBlockingThread;
import com.zz.schedule.interceptor.utils.MyFileUtils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzuizui on 2018/1/23.
 */
public class CommonClient {

    private static final String BACK_PATH;
    private static final long initialDelay = 5;
    private static final long period = 1;
    private final ScheduledExecutorService service;
    final ConcurrentHashMap<String, String> lockMap = new ConcurrentHashMap();
    private ConcurrentHashMap<String, Map<String, String>> dataCache = new ConcurrentHashMap();

    static {
        BACK_PATH = System.getProperty("user.home") + File.separator + "config_center" + File.separator;
    }

    public CommonClient(){
        this.service = Executors.newScheduledThreadPool(3);
        // 从现在开始5秒钟之后，每隔1秒钟执行一次job1
        service.scheduleAtFixedRate(new FetchBlockingThread(BACK_PATH), initialDelay, period, TimeUnit.SECONDS);
    }

    public static void ergodic(File file, Map<String, String> map) {
        File[] files = file.listFiles();
        if(files==null){
            System.out.println("sssssss");
            return;
        }
        for (File f : files) {
            // 判断是否文件夹
            if(f.isDirectory()){
                // 调用自身,查找子目录
                ergodic(f, map);
            }else {
                String[] array = f.getName().split("\\.");
                String fileValue = MyFileUtils.getContent(f);
                map.put(array[0].toUpperCase(),fileValue);
            }
        }
    }
}
