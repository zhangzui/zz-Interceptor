package com.zz.schedule.interceptor.switcher.config;

import com.zz.schedule.interceptor.switcher.bean.SwitchBaseConfig;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangzuizui on 2018/1/16.
 */
public class SwitchConfig {

    private static final String DEFAULT_KEY = "DEFAULT";

    public static Map<String, List<SwitchBaseConfig>> switcherConfigMap;

    public static List<SwitchBaseConfig> getSwitchConfig(String key){
        if(switcherConfigMap != null){
            List<SwitchBaseConfig> switchBeanList = switcherConfigMap.get(key);
            if(CollectionUtils.isEmpty(switchBeanList)){
                switchBeanList = switcherConfigMap.get(DEFAULT_KEY);
            }
            return switchBeanList;
        }else {
            throw new RuntimeException("switcherConfigMap is null, please chack the config files in classPath/interceptor/");
        }
    }
}
