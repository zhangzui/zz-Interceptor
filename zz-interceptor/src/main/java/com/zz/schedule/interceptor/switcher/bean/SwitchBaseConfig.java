package com.zz.schedule.interceptor.switcher.bean;

/**
 * Created by zhangzuizui on 2018/1/17.
 */
public class SwitchBaseConfig {
    //开关名
    private String switcherName;
    //优先级
    private int priority;
    //开关状态
    private String switchStatus = "ON";

    public String getSwitcherName() {
        return switcherName;
    }

    public void setSwitcherName(String switcherName) {
        this.switcherName = switcherName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(String switchStatus) {
        this.switchStatus = switchStatus;
    }
}
