package com.zz.schedule.interceptor.switcher.switcher;

import com.zz.schedule.interceptor.switcher.bean.BlackAndWhiteSwitchConfig;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by zhangzuizui on 2018/1/16.
 * 黑白名单开关
 */
public class BlackAndWhiteListSwitcher extends AbstractSwitcher<BlackAndWhiteSwitchConfig> {

    @Override
    protected boolean doSwitchExecute(BlackAndWhiteSwitchConfig blackAndWhiteSwitchConfig) {

        boolean result = false;
        List<String> writeList = blackAndWhiteSwitchConfig.getWhiteList();
        List<String> blackList = blackAndWhiteSwitchConfig.getBlackList();

        if(CollectionUtils.isNotEmpty(writeList)){
            if(writeList.contains(getSwitchDimension().getPin())){
                return true;
            }else {
                return false;
            }
        }

        if(CollectionUtils.isNotEmpty(blackList)) {
            if (blackList.contains(getSwitchDimension().getPin())) {
                return false;
            } else {
                return true;
            }
        }

        return result;
    }
}
