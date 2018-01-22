package com.zz.interceptor.switcher.test;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.zz.interceptor.utils.GsonUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzuizui on 2018/1/19.
 */
public class TestGetJsonFile {

    public static void main(String[] args) {
        getPath();
    }
    public static void getPath() {
        String path = TestGetJsonFile.class.getClassLoader().getResource("default.json").toString();
        path = path.replace("\\", "/");
        if (path.contains(":")) {
            path = path.replace("file:/","");// 2
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
