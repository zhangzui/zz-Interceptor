package com.zz.schedule.interceptor.switcher.switcher;


import com.zz.schedule.interceptor.switcher.bean.SwitchBaseConfig;

/**
 * Created by zhangzuizui on 2018/1/16.
 */
public interface Switcher <T extends SwitchBaseConfig>{

    /**
     * 开关有效性
     */
    boolean isEffect(T t);

    /**
     * 开关
     * @return
     */
    boolean doSwitch(T t);

}
