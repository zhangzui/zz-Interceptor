package com.zz.interceptor.switcher.test;

import com.zz.interceptor.switcher.SwitchManager;
import com.zz.interceptor.switcher.bean.SwitchDimension;
import com.zz.interceptor.switcher.config.SwitchConfig;
import com.zz.interceptor.switcher.handle.CommonClient;
import com.zz.interceptor.switcher.handle.ConfClientByJSON;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by zhangzuizui on 2018/1/18.
 */
public class TestSwitch {

    @Before
    public void beforeTest(){
        CommonClient commonClient = new CommonClient();
    }
    /**
     * 白名单测试
     */
    @Test
    public void testList(){
        if(SwitchManager.getSwitchManager().doSwitch(new SwitchDimension("default","ordertest408"))){
            System.out.println("ssss");
            return;
        }
        System.out.println("执行逻辑-----");
    }

    /**
     * 百分比测试
     */
    @Test
    public void percentTest(){
        SwitchConfigByJson switchConfigByJson = new SwitchConfigByJson();
        SwitchConfig.switcherConfigMap = switchConfigByJson.getConfig();
        //使用，命中开关
        if(SwitchManager.getSwitchManager().doSwitch(new SwitchDimension("percentTest","zzz"))){
            System.out.println("ssss");
            return;
        }
        System.out.println("执行逻辑-----");
    }

    /**
     * 时间测试
     */
    @Test
    public void timeTest(){
        SwitchConfigByJson switchConfigByJson = new SwitchConfigByJson();
        SwitchConfig.switcherConfigMap = switchConfigByJson.getConfig();
        //使用，命中开关
        if(SwitchManager.getSwitchManager().doSwitch(new SwitchDimension("timeTest",null))){
            System.out.println("ssss");
            return;
        }
        System.out.println("执行逻辑-----");
    }
     /**
     * 多维度测试
     */
    @Test
    public void allTest(){
        SwitchConfigByJson switchConfigByJson = new SwitchConfigByJson();
        SwitchConfig.switcherConfigMap = switchConfigByJson.getConfig();
        //使用，命中开关
        if(SwitchManager.getSwitchManager().doSwitch(new SwitchDimension("allTest","ZZZ"))){
            System.out.println("ssss");
            return;
        }
        System.out.println("执行逻辑-----");
    }


}