package com.zz.schedule.jdSchedule;

import com.zz.schedule.jdSchedule.common.BeanCommonFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhangzuizui on 2018/1/25.
 */
public class StartMain {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-schedule.xml");

        BeanCommonFactory.setApplicationContext(context);

        synchronized (StartMain.class) {
            while (true) {
                try {
                    StartMain.class.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
