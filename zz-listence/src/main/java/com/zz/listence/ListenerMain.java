package com.zz.listence;

import com.zz.listence.bean.SmsEvent;
import com.zz.listence.common.EventProcessor;

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
