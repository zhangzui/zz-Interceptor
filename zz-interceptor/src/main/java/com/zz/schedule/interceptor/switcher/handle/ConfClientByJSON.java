package com.zz.schedule.interceptor.switcher.handle;

import com.zz.schedule.interceptor.utils.SwitchUtils;
import com.zz.schedule.interceptor.switcher.config.ConfClient;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangzuizui on 2018/1/23.
 */
public class ConfClientByJSON extends CommonClient implements ConfClient {

    private static String defaultPath = null;
    static {
        defaultPath = ConfClientByJSON.class.getClassLoader().getResource("interceptor/").toString();
    }
    public ConfClientByJSON(String path){
        System.out.println("Refresh========");
        initData(path);
    }

    @Override
    public void initData(String path){
        initConfig(subscribePath(path));
    }

    @Override
    public Map subscribePath(String path){
        Map<String, String> map = new HashMap<String, String>();
        String filePath = this.getClass().getClassLoader().getResource(path).getPath();

        ergodic(new File(filePath),map);
        return map;
    }

    @Override
    public void initConfig(Map map) {
        SwitchUtils.initSwitchConfig(map);
    }
}
