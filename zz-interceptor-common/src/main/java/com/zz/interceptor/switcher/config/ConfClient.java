package com.zz.interceptor.switcher.config;

import java.util.Map;

/**
 * Created by zhangzuizui on 2018/1/23.
 */
public interface ConfClient {

    void initData(String path);

    Map subscribePath(String path);

    void initConfig(Map map);

}
