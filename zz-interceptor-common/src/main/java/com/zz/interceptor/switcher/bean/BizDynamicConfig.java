package com.zz.interceptor.switcher.bean;

import java.io.Serializable;

/**
 * Created by zhangzuizui on 2018/1/22.
 */
public class BizDynamicConfig implements Serializable {

    //key-业务类型
    private String bizType;

    //value-开关配置
    private String jsonConfig;

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getJsonConfig() {
        return jsonConfig;
    }

    public void setJsonConfig(String jsonConfig) {
        this.jsonConfig = jsonConfig;
    }
}
