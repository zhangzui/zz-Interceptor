package com.zz.interceptor.switcher.switcher;

import com.zz.interceptor.switcher.bean.TimeRangeSwitchConfig;
import com.zz.interceptor.utils.TimeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by zhangzuizui on 2018/1/16.
 * 时间开关
 */
public class TimeRangeSwitcher extends AbstractSwitcher<TimeRangeSwitchConfig> {

    private static Logger logger = LoggerFactory.getLogger(BlackAndWhiteListSwitcher.class);

    @Override
    public boolean doSwitchExecute(TimeRangeSwitchConfig timeRangeSwitchConfig) {
        boolean result = false;
        try {
            String startTimeString = timeRangeSwitchConfig.getStartTime();
            String endTimeString = timeRangeSwitchConfig.getEndTime();
            Date date = new Date();
            if(StringUtils.isNotBlank(startTimeString) && StringUtils.isNotBlank(endTimeString)){
                Date startDate = TimeUtils.parseDate("yyyy-MM-dd HH:mm:ss",startTimeString);
                Date endDate = TimeUtils.parseDate("yyyy-MM-dd HH:mm:ss",endTimeString);
                if(date.after(startDate) && date.before(endDate)){
                    result = true;
                }
            }else if(StringUtils.isNotBlank(startTimeString) && StringUtils.isBlank(endTimeString)){
                Date startDate = TimeUtils.parseDate("yyyy-MM-dd HH:mm:ss",startTimeString);
                if(date.after(startDate)){
                    result = true;
                }
            }else if(StringUtils.isBlank(startTimeString) && StringUtils.isNotBlank(endTimeString)){
                Date endDate = TimeUtils.parseDate("yyyy-MM-dd HH:mm:ss",endTimeString);
                if(date.before(endDate)){
                    result = true;
                }
            }
        } catch (Exception e) {
             logger.info("TimeRangeSwitcher,error!!!");
        }
        return result;
    }
}
