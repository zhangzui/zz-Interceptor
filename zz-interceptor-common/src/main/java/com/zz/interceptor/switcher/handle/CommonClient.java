package com.zz.interceptor.switcher.handle;

import com.zz.interceptor.utils.MyFileUtils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangzuizui on 2018/1/23.
 */
public class CommonClient {

    private static final String BACK_PATH;
    final ConcurrentHashMap<String, String> lockMap = new ConcurrentHashMap();
    private ConcurrentHashMap<String, Map<String, String>> dataCache = new ConcurrentHashMap();

    static {
        BACK_PATH = System.getProperty("user.home") + File.separator + "config_center" + File.separator;
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
