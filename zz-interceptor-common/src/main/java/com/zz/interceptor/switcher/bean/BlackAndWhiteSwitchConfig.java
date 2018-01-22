package com.zz.interceptor.switcher.bean;

import java.util.List;

/**
 * Created by zhangzuizui on 2018/1/17.
 */
public class BlackAndWhiteSwitchConfig extends SwitchBaseConfig {

    //白名单
    private List<String> whiteList;

    //黑名单
    private List<String> blackList;

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }

    public List<String> getBlackList() {
        return blackList;
    }

    public void setBlackList(List<String> blackList) {
        this.blackList = blackList;
    }
}
