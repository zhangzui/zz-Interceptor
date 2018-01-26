package com.zz.schedule.interceptor.switcher.test;

import com.zz.schedule.interceptor.switcher.SwitchManager;
import com.zz.schedule.interceptor.switcher.bean.SwitchDimension;
import com.zz.schedule.interceptor.switcher.handle.CommonClient;
import com.zz.schedule.interceptor.switcher.handle.ConfClientByJSON;

/**
 * Created by zhangzuizui on 2018/1/25.
 */
public class StartMain {

    public static void main(String[] args) {
        new ConfClientByJSON("interceptor");
        new CommonClient();
        if(SwitchManager.getSwitchManager().doSwitch(new SwitchDimension("default","ordertest408"))){
            System.out.println("ssss");
            return;
        }
        System.out.println("执行逻辑-----");
    }
}
