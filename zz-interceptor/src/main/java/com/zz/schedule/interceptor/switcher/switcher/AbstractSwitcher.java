package com.zz.schedule.interceptor.switcher.switcher;

import com.alibaba.fastjson.JSON;
import com.zz.schedule.interceptor.switcher.bean.SwitchDimension;
import com.zz.schedule.interceptor.switcher.bean.SwitchBaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangzuizui on 2018/1/16.
 */
public abstract class AbstractSwitcher<T extends SwitchBaseConfig> implements Switcher<T> {

    private static Logger logger = LoggerFactory.getLogger(AbstractSwitcher.class);
    private static final String ON_SWITCH = "ON";

    protected SwitchDimension switchDimension = new SwitchDimension();


    protected abstract boolean doSwitchExecute(T t);

    @Override
    public boolean doSwitch(T t) {
        logger.info("AbstractSwitcher,paramConfig:{} ",JSON.toJSONString(t));
        return doSwitchExecute(t);
    }
    @Override
    public boolean isEffect(T t) {
        if(t.getSwitchStatus().equals(ON_SWITCH)){
            return true;
        }else {
            return false;
        }
    }

    public SwitchDimension getSwitchDimension() {
        return switchDimension;
    }

    public void setSwitchDimension(SwitchDimension switchDimension) {
        this.switchDimension = switchDimension;
    }
}
