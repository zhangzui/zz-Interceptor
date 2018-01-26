package com.zz.schedule.interceptor.switcher.switcher;

import com.zz.schedule.interceptor.switcher.bean.PercentSwitchConfig;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zhangzuizui on 2018/1/16.
 * 百分比开关
 */
public class PercentSwitcher extends AbstractSwitcher<PercentSwitchConfig> {

    @Override
    protected boolean doSwitchExecute(PercentSwitchConfig percentSwitchConfig) {
        boolean result = false;
        if(StringUtils.isNotBlank(percentSwitchConfig.getBasePercentNum())){
            int hashCode = Math.abs(getSwitchDimension().getPin().hashCode());
            int index = hashCode % Integer.parseInt(percentSwitchConfig.getBasePercentNum()) ;
            if(index >= Integer.parseInt(percentSwitchConfig.getStartIndex()) && index< Integer.parseInt(percentSwitchConfig.getEndIndex())){
                result = true;
            }
        }
        return result;
    }
}
