package com.zz.schedule.interceptor.switcher.bean;

import com.zz.schedule.interceptor.switcher.switcher.BlackAndWhiteListSwitcher;
import com.zz.schedule.interceptor.switcher.switcher.PercentSwitcher;
import com.zz.schedule.interceptor.switcher.switcher.Switcher;
import com.zz.schedule.interceptor.switcher.switcher.TimeRangeSwitcher;

/**
 * Created by zhangzuizui on 2018/1/12.
 */
public enum SwitchEnum {

    BLACK_AND_WHITE("blackAndWhiteListSwitcher", BlackAndWhiteListSwitcher.class, BlackAndWhiteSwitchConfig.class ),
    PERCENT_SWITCH("percentSwitch", PercentSwitcher.class, PercentSwitchConfig.class),
    TIME_SWITCH("timeSwitch", TimeRangeSwitcher.class, TimeRangeSwitchConfig.class);

    private String switchName;
    private Class<? extends Switcher> switcherClassName;
    private Class<? extends SwitchBaseConfig> configClassName;

    SwitchEnum(String switchName, Class<? extends Switcher> switcherClassName, Class<? extends SwitchBaseConfig> configClassName) {
        this.switchName = switchName;
        this.switcherClassName = switcherClassName;
        this.configClassName = configClassName;
    }

    public String getSwitchName() {
        return switchName;
    }

    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

    public Class<? extends Switcher> getSwitcherClassName() {
        return switcherClassName;
    }

    public void setSwitcherClassName(Class<? extends Switcher> switcherClassName) {
        this.switcherClassName = switcherClassName;
    }

    public Class<? extends SwitchBaseConfig> getConfigClassName() {
        return configClassName;
    }

    public void setConfigClassName(Class<? extends SwitchBaseConfig> configClassName) {
        this.configClassName = configClassName;
    }

    public static Class<? extends Switcher> getSwitcherByName(String switchName){
        for (SwitchEnum e : SwitchEnum.values()) {
            if(switchName.equals(e.getSwitchName())){
                return e.getSwitcherClassName();
            }
        }
        return null;
    }

    public static Class<? extends SwitchBaseConfig> getSwitchConfigByName(String switchName){
        for (SwitchEnum e : SwitchEnum.values()) {
            if(switchName.equals(e.getSwitchName())){
                return e.getConfigClassName();
            }
        }
        return null;
    }
}
