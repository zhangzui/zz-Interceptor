package com.zz.schedule.listence.events;

import com.zz.schedule.listence.common.Event;
import com.zz.schedule.listence.common.EventListener;

/**
 * Creaded by zhangzuizui on 18-1-24
 */
public class SmsEventListener implements EventListener {

    private static SmsEventListener smsEventListener = new SmsEventListener();

    private SmsEventListener(){
        
    }
    public static SmsEventListener getSmsEventListener(){
        return smsEventListener;
    }
    @Override
    public void handle(Event event) {
        System.out.println("发送短信"+event.getId()+"-"+event.getCreateTime());
    }
}
