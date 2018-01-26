package com.zz.schedule.interceptor.switcher.bean;


/**
 * Created by zhangzuizui on 2018/1/11.
 */
public class SwitchDimension {

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 目标pin
     */
    private String pin;

    public SwitchDimension(String bizType, String pin) {
        this.bizType = bizType;
        this.pin = pin;
    }
    public SwitchDimension() {

    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
