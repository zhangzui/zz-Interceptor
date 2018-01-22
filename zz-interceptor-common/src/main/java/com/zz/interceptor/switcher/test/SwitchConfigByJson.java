package com.zz.interceptor.switcher.test;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.zz.interceptor.switcher.bean.SwitchBaseConfig;
import com.zz.interceptor.switcher.bean.SwitchEnum;
import com.zz.interceptor.utils.GsonUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzuizui on 2018/1/18.
 */
public class SwitchConfigByJson {

    public Map<String, List<SwitchBaseConfig>> getConfig() {
        String whiteListTest = "[{\"switchorName\":\"blackAndWhiteListSwitcher\",\"priority\":1,\"switchStatus\":\"ON\",\"whiteList\":[\"asas\",\"zzz\"]}]";
        String percentTest = "[{\"switchorName\":\"percentSwitch\",\"priority\":2,\"switchStatus\":\"ON\",\"percent\":\"1/1\"}]";
        String timeTest = "[{\"switchorName\":\"timeSwitch\",\"priority\":3,\"switchStatus\":\"ON\",\"startTime\":\"2018-01-18 17:18:00\",\"endTime\":\"2018-01-19 17:18:00\"}]";

        String allTest = "[{\"switchorName\":\"blackAndWhiteListSwitcher\",\"priority\":1,\"switchStatus\":\"ON\",\"whiteList\":[\"asas\",\"sasa\"]},{\"switchorName\":\"percentSwitch\",\"priority\":2,\"switchStatus\":\"ON\",\"percent\":\"10/10\"},{\"switchorName\":\"timeSwitch\",\"priority\":3,\"switchStatus\":\"ON\",\"startTime\":\"2018-01-16 17:18:00\",\"endTime\":\"2018-01-16 17:18:00\"}]";

        Map<String,List<SwitchBaseConfig>> switchBeanMap = new HashMap<String,List<SwitchBaseConfig>>();

        getSwitchBaseConfigs("whiteListTest",whiteListTest,switchBeanMap);
        getSwitchBaseConfigs("percentTest", percentTest,switchBeanMap);
        getSwitchBaseConfigs("timeTest", timeTest,switchBeanMap);
        getSwitchBaseConfigs("allTest", allTest,switchBeanMap);

        return switchBeanMap;
    }

    private void getSwitchBaseConfigs(String key, String config, Map<String, List<SwitchBaseConfig>> switchBeanMap) {
        List<SwitchBaseConfig> configList = new ArrayList<SwitchBaseConfig>();
        List<JSONObject> switchConfigList = GsonUtils.fromJson(config, new TypeToken<List<JSONObject>>() {});

        for (JSONObject object : switchConfigList){
            String beanClassName = object.get("switchorName").toString();
            SwitchBaseConfig switchBaseConfig = (SwitchBaseConfig) GsonUtils.fromJson(object.toJSONString(), SwitchEnum.getSwitchConfigByName(beanClassName));
            configList.add(switchBaseConfig);
        }

        if (CollectionUtils.isNotEmpty(configList)) {
            switchBeanMap.put(key.toUpperCase(), configList);
        }
    }
}
