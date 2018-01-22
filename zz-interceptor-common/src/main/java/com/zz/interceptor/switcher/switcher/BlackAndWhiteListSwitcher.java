package com.zz.interceptor.switcher.switcher;

import com.zz.interceptor.switcher.bean.BlackAndWhiteSwitchConfig;
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

        if(CollectionUtils.isNotEmpty(writeList) && writeList.contains(getSwitchDimension().getPin())){
            result = true;
        }else if(CollectionUtils.isNotEmpty(blackList) && writeList.contains(getSwitchDimension().getPin())){
            result = true;
        }
        return result;
    }
}
