package com.zz.schedule.listence.common;

import com.zz.schedule.listence.utils.TimeUtils;

import java.util.Date;
import java.util.UUID;

/**
 * Creaded by zhangzuizui on 18-1-24
 * 事件
 */
public abstract class Event {

    private String id;
    private String eventType;
    private String eventName;
    private String createTime;

    public Event() {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
        this.eventName = this.getClass().getName();
        this.eventType = "DEFAULT";
        String curtime = TimeUtils.formatDate(TimeUtils.yyyyMMddHHmmss, new Date());
        this.createTime = curtime;
        this.createTime = curtime;
    }

    public Event(String id, String eventType, String eventName, String createTime) {
        this.id = id;
        this.eventType = eventType;
        this.eventName = eventName;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public abstract EventListener bindEventListener();
    
}
