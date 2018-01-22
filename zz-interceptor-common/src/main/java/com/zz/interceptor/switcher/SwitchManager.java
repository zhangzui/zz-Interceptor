package com.zz.interceptor.switcher;

import com.alibaba.fastjson.JSON;
import com.zz.interceptor.switcher.bean.SwitchBaseConfig;
import com.zz.interceptor.switcher.bean.SwitchDimension;
import com.zz.interceptor.switcher.bean.SwitchEnum;
import com.zz.interceptor.switcher.config.SwitchConfig;
import com.zz.interceptor.switcher.switcher.AbstractSwitcher;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangzuizui on 2018/1/16.
 */
public class SwitchManager {

    private static Logger logger = LoggerFactory.getLogger(SwitchManager.class);

    private static HashMap<String,AbstractSwitcher> switcherClassMap = new HashMap<String,AbstractSwitcher>();

    private static final SwitchManager switchManager = new SwitchManager();

    public static SwitchManager getSwitchManager(){
       return switchManager;
    }

    private SwitchManager() {
        initSwitcherClassMap();
    }

    private void initSwitcherClassMap() {
        for (SwitchEnum switchEnum : SwitchEnum.values()) {
            try {
                switcherClassMap.put(switchEnum.getSwitchName(), (AbstractSwitcher) switchEnum.getSwitcherClassName().newInstance());
            } catch (Exception e) {
                logger.error("initSwitcherClassMap,Exception,{}",e.getMessage());
            }
        }
    }

    public boolean doSwitch(SwitchDimension switchDimension) {
        logger.info("switchDimension:{}", JSON.toJSONString(switchDimension));

        boolean result = false;
        long startTime = System.currentTimeMillis();
        try {
            List<SwitchBaseConfig> switchBaseConfigs = SwitchConfig.getSwitchConfig(switchDimension.getBizType().toUpperCase());
            if(CollectionUtils.isNotEmpty(switchBaseConfigs)){
                for(SwitchBaseConfig switchBean : switchBaseConfigs){
                    AbstractSwitcher abstractSwitcher = switcherClassMap.get(switchBean.getSwitcherName());
                    abstractSwitcher.setSwitchDimension(switchDimension);
                    if(!abstractSwitcher.isEffect(switchBean)){
                        continue;
                    }
                    result = abstractSwitcher.doSwitch(switchBean);
                    if(!result){
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("doSwitch,error:{}", e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        logger.info("result:{},开关执行耗时ms：{}", result, endTime-startTime );
        return result;
    }
}
