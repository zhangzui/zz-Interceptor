package com.zz.interceptor.switcher.test;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.zz.interceptor.switcher.handle.CommonClient;
import com.zz.interceptor.utils.GsonUtils;
import com.zz.interceptor.utils.MyFileUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author zhangzuizui
 * @date 2018/1/19
 */
public class TestGetJsonFile {

    public static void main(String[] args) {
        CommonClient commonClient = new CommonClient();
    }

    private static void ergodic(File file) {
        File[] files = file.listFiles();
        if(files==null){
            return;
        }
        for (File f : files) {
            // 判断是否文件夹
            if(f.isDirectory()){
                // 调用自身,查找子目录
                ergodic(f);
            }else {
                System.out.println(f.getAbsolutePath());
                System.out.println(MyFileUtils.getContent(f));
            }
        }
    }

}
