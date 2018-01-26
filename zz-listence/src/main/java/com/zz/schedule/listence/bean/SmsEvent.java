package com.zz.schedule.listence.bean;

import com.zz.schedule.listence.common.Event;
import com.zz.schedule.listence.common.EventListener;
import com.zz.schedule.listence.events.SmsEventListener;

/**
 * Creaded by zhangzuizui on 18-1-24
 */
public class SmsEvent extends Event {
    
    @Override
    public EventListener bindEventListener() {
        return SmsEventListener.getSmsEventListener();
    }
}
