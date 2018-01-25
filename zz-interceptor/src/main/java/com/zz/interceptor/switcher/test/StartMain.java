package com.zz.interceptor.switcher.test;

import com.zz.interceptor.switcher.SwitchManager;
import com.zz.interceptor.switcher.bean.SwitchDimension;
import com.zz.interceptor.switcher.handle.CommonClient;
import com.zz.interceptor.switcher.handle.ConfClientByJSON;

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
