package com.zz.schedule.jdSchedule;

import com.zz.schedule.jdSchedule.common.AbstractJob;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzuizui on 2018/1/25.
 */
@Component
public class TestJob extends AbstractJob{

    @Override
    protected void doRealJob() throws Exception {
        System.out.println("doRealJob------------------");
    }
}
