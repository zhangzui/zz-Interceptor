package com.zz.schedule.interceptor.switcher.bean;

/**
 * Created by zhangzuizui on 2018/1/17.
 */
public class PercentSwitchConfig extends SwitchBaseConfig {

    //区间开始位置
    private String startIndex;
    //区间结束位置
    private String endIndex;
    //百分比基数
    private String basePercentNum;

    public String getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }

    public String getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(String endIndex) {
        this.endIndex = endIndex;
    }

    public String getBasePercentNum() {
        return basePercentNum;
    }

    public void setBasePercentNum(String basePercentNum) {
        this.basePercentNum = basePercentNum;
    }
}
