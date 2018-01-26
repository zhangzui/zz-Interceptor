package com.zz.schedule.interceptor.switcher.bean;

/**
 * Created by zhangzuizui on 2018/1/17.
 */
public class TimeRangeSwitchConfig extends SwitchBaseConfig {

    //开始时间
    private String startTime;

    //结束时间
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
