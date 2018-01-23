package com.zz.interceptor.switcher.test;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.zz.interceptor.utils.GsonUtils;
import com.zz.interceptor.utils.MyFileUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzuizui on 2018/1/19.
 */
public class TestGetJsonFile {

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getContextClassLoader().getResource("interceptor/").getPath());
        System.out.println(TestGetJsonFile.class.getClassLoader().getResource("interceptor/").toString());
        System.out.println(System.getProperty("user.dir"));
        String path = TestGetJsonFile.class.getClassLoader().getResource("interceptor/").getPath();
        ergodic(new File(path));
    }

    private static void ergodic(File file) {
        File[] files = file.listFiles();
        if(files==null){
            System.out.println("sssssss");
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

    public static void getPath() {

        String BACK_PATH = System.getProperty("user.home") + File.separator + "config_center" + File.separator;

        String path = TestGetJsonFile.class.getClassLoader().getResource("interceptor/default.json").toString();

        path = path.replace("\\", "/");
        if (path.contains(":")) {
            path = path.replace("file:/","");
        }


        System.out.println(path);
        List<JSONObject> switchConfigList = new ArrayList<JSONObject>();

        try {
            String input = FileUtils.readFileToString(new File(path), "UTF-8");
            switchConfigList = GsonUtils.fromJson(input, new TypeToken<List<JSONObject>>() {});
        } catch (Exception e) {
        }

        System.out.println(GsonUtils.toJson(switchConfigList));
    }

}
