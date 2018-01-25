package com.zz.listence.bean;

import com.zz.listence.common.Event;
import com.zz.listence.common.EventListener;
import com.zz.listence.events.SmsEventListener;

/**
 * Creaded by zhangzuizui on 18-1-24
 */
public class SmsEvent extends Event{
    
    @Override
    public EventListener bindEventListener() {
        return SmsEventListener.getSmsEventListener();
    }
}
