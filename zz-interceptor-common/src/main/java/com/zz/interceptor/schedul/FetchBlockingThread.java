package com.zz.interceptor.schedul;

import com.zz.interceptor.switcher.handle.CommonClient;
import com.zz.interceptor.switcher.handle.ConfClientByJSON;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangzuizui on 2018/1/24.
 */
public class FetchBlockingThread implements Runnable {

    private String path;
    private ConcurrentHashMap<String, String> map;
    public FetchBlockingThread(String path, ConcurrentHashMap<String, String> map) {
        this.path = path;
    }

    @Override
    public void run() {
        ConfClientByJSON confClientByJSON = new ConfClientByJSON(path);
    }
}
