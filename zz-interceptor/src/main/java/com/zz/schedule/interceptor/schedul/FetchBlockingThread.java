package com.zz.schedule.interceptor.schedul;

import com.zz.schedule.interceptor.switcher.handle.ConfClientByJSON;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangzuizui on 2018/1/24.
 */
public class FetchBlockingThread implements Runnable {

    private String path;
    private ConcurrentHashMap<String, String> map;
    public FetchBlockingThread(String path) {
        this.path = path;
    }

    @Override
    public void run() {
        ConfClientByJSON confClientByJSON = new ConfClientByJSON("interceptor");
    }
}
