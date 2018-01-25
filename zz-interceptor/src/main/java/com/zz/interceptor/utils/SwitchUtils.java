package com.zz.interceptor.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.zz.interceptor.switcher.bean.BizDynamicConfig;
import com.zz.interceptor.switcher.bean.SwitchBaseConfig;
import com.zz.interceptor.switcher.bean.SwitchEnum;
import com.zz.interceptor.switcher.config.SwitchConfig;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by zhangzuizui on 2018/1/22.
 */
public class SwitchUtils {

    static final Logger logger = LoggerFactory.getLogger(SwitchUtils.class);
    private static final String SWITCHER_NAME = "switcherName";

    public static void initSwitchConfig(Map<String, String> map) {
        //初始化开关配置信息
        if(map != null){
            try {
                SwitchConfig.switcherConfigMap = getConfigByList(SwitchUtils.transformDynamicConfig(map));
                logger.info("SwitchConfig.SwitcherConfigMap:{}", JSON.toJSONString(SwitchConfig.switcherConfigMap));
            } catch (Exception e) {
                logger.error("SwitchUccConfigListener.getConfigByList is error",e.getMessage());
            }
        }else {
            logger.info("SwitchUccConfigListener.config is null");
        }
    }

    private static Map<String, List<SwitchBaseConfig>> getConfigByList(List<BizDynamicConfig> bizDynamicConfigs) {
        Map<String,List<SwitchBaseConfig>> switchBeanMap = new HashMap<String,List<SwitchBaseConfig>>();
        for (BizDynamicConfig bizDynamicConfig : bizDynamicConfigs){
            Object value = bizDynamicConfig.getJsonConfig();
            if(value != null){
                List<SwitchBaseConfig> configList = new ArrayList<SwitchBaseConfig>();
                List<JSONObject> switchConfigList = GsonUtils.fromJson(value.toString(), new TypeToken<List<JSONObject>>() {});

                for (JSONObject object : switchConfigList){
                    String beanClassName = object.get(SWITCHER_NAME).toString();
                    SwitchBaseConfig switchBaseConfig = (SwitchBaseConfig) GsonUtils.fromJson(object.toJSONString(), SwitchEnum.getSwitchConfigByName(beanClassName));
                    configList.add(switchBaseConfig);
                }
                if (CollectionUtils.isNotEmpty(configList)) {
                    //优先级排序
                    Collections.sort(configList, new Comparator<SwitchBaseConfig>() {
                        @Override
                        public int compare(SwitchBaseConfig o1, SwitchBaseConfig o2) {
                            if(o1.getPriority() > o2.getPriority()){
                                return 1;
                            }else {
                                return -1;
                            }
                        }
                    });
                    switchBeanMap.put(bizDynamicConfig.getBizType().toUpperCase(), configList);
                }
            }
        }
        return switchBeanMap;
    }

    public static List<BizDynamicConfig> transformDynamicConfig(Map<String, String> map) {
        List<BizDynamicConfig> bizDynamicConfigList = new ArrayList<BizDynamicConfig>();
        if(map != null){
            for (Map.Entry entry:map.entrySet()) {
                BizDynamicConfig bizDynamicConfig = new BizDynamicConfig();
                String key = entry.getKey().toString().toUpperCase();
                Object value = entry.getValue();
                bizDynamicConfig.setBizType(key);
                bizDynamicConfig.setJsonConfig(value.toString());
                bizDynamicConfigList.add(bizDynamicConfig);
            }
        }
        return bizDynamicConfigList;
    }

    @Deprecated
    private static Map<String, List<SwitchBaseConfig>> getConfig(Map<String, String> map) {
        Map<String,List<SwitchBaseConfig>> switchBeanMap = new HashMap<String,List<SwitchBaseConfig>>();

        for (Map.Entry entry:map.entrySet()) {
            Object value = entry.getValue();
            if(value != null){
                List<SwitchBaseConfig> configList = new ArrayList<SwitchBaseConfig>();
                List<JSONObject> switchConfigList = GsonUtils.fromJson(value.toString(), new TypeToken<List<JSONObject>>() {});

                for (JSONObject object : switchConfigList){
                    String beanClassName = object.get("switchorName").toString();
                    SwitchBaseConfig switchBaseConfig = (SwitchBaseConfig) GsonUtils.fromJson(object.toJSONString(), SwitchEnum.getSwitchConfigByName(beanClassName));
                    configList.add(switchBaseConfig);
                }
                if (CollectionUtils.isNotEmpty(configList)) {
                    //优先级排序
                    Collections.sort(configList, new Comparator<SwitchBaseConfig>() {
                        @Override
                        public int compare(SwitchBaseConfig o1, SwitchBaseConfig o2) {
                            if(o1.getPriority() > o2.getPriority()){
                                return 1;
                            }else {
                                return -1;
                            }
                        }
                    });
                    switchBeanMap.put(entry.getKey().toString().toUpperCase(), configList);
                }
            }
        }
        return switchBeanMap;
    }

}
