package com.zz.schedule.jdSchedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangyin.schedule.client.ScheduleClient;
import com.wangyin.schedule.client.exception.JobInitException;
import com.wangyin.schedule.client.job.ScheduleContext;
import com.wangyin.schedule.client.job.SchedulerJob;
import com.wangyin.schedule.sdk.ScheduleSdk;
import com.wangyin.schedule.sdk.request.JobAddSimpleRequest;
import com.wangyin.schedule.sdk.request.TaskGetRequest;
import com.wangyin.schedule.sdk.response.JsonObjectResponse;
import com.wangyin.schedule.sdk.response.TaskGetResponse;
import com.wangyin.schedule.utils.TaskStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangzuizui on 2018/1/25.
 */
public class TestSchedule {

    private static final ScheduleSdk sdk;

    static {
        String host = "http://172.25.47.136:8080";
        String appId = "overseas_iou_job";
        String secret = "cff9f8e8b0e5ce2d002c5bf745a311a9";
        sdk = new ScheduleSdk(host, appId, secret);
    }

    public static void main(String[] args) {
      //  scheduleRequest();
        scheduleResponse();
    }
    public static void scheduleRequest(){
        JobAddSimpleRequest req = new JobAddSimpleRequest();
        req.setJobClass("TestJob");
        Map map = new HashMap<String, String>();
        map.put("key1", "key1value");
        map.put("key2", "key2value");
        req.setParams(map);
        req.setExpired(300);
        req.addNotification(TaskStatus.UNACCEPTED);
        req.addNotification(TaskStatus.ARCHIVE);
        try {
            JsonObjectResponse res = sdk.execute(req);
            System.out.println("请求：-------------"+JSON.toJSONString(res));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void scheduleResponse(){
        TaskGetResponse response = null;
        try {
            response = sdk.execute(new TaskGetRequest(159218199));
            System.out.println("响应：-------------"+JSON.toJSONString(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jobClass = response.getJobClass();

        try {
            SchedulerJob object = (SchedulerJob) Class.forName(jobClass).newInstance();
            ScheduleClient scheduleClient = new ScheduleClient();
            ScheduleContext scheduleContext = new ScheduleContext(response,scheduleClient);
            object.doJob(scheduleContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
