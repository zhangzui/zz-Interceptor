package com.zz.schedule.listence;

import com.zz.schedule.listence.bean.SmsEvent;
import com.zz.schedule.listence.common.EventProcessor;

/**
 * Creaded by zhangzuizui on 18-1-24
 */
public class ListenerMain {
    
    public static void main(String[] args) {
        
        EventProcessor.getInstance().startEvent();
        for (int i = 0; i < 10; i++) {
            SmsEvent smsEvent = new SmsEvent();
            EventProcessor.getInstance().addEvent(smsEvent);
        }
    }
}
