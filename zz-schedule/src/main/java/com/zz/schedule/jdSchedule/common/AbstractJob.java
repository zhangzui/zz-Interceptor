package com.zz.schedule.jdSchedule.common;

import com.alibaba.fastjson.JSON;
import com.wangyin.schedule.client.job.ScheduleContext;
import com.wangyin.schedule.client.job.SchedulerJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Function: 所有任务的基类，对所有任务的封装，目的是使任务只关系业务逻辑。
 * 所有非业务的逻辑（如分库分表路由）都在此封装。
 * Note:主要用于分表
 * <p>
 * Created by Yancey on 2016-10-10 17:03.
 */
public abstract class AbstractJob implements SchedulerJob {

    private static final Logger logger = LoggerFactory.getLogger(AbstractJob.class);

//    @Resource(name = "exePreTableThreadPool")
//    private ThreadPoolTaskExecutor exePreTableThreadPool = new ThreadPoolTaskExecutor(0x3);
//
    private ExecutorService exec = Executors.newFixedThreadPool(5);


    @Override
    public void doJob(ScheduleContext scheduleContext) throws Exception {
        //final Map<String, String> params = scheduleContext.getParameters();
        final String jobName = this.getClass().getSimpleName();
        logger.info("执行[{}]任务，参数{}", jobName, JSON.toJSONString(scheduleContext));

        if (!ifExe(jobName)) {
            logger.info("[{}]任务校验不执行，终止", jobName);
            return;
        }
        exec.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 真正执行的原子逻辑
                    doRealJob();
                } catch (Exception e) {
                    logger.error("xxxxxxxxx", e);
                } finally {

                }
            }
        });

        logger.info("执行[{}]任务完毕，库序号:{},执行数量:{},未执行数量:{}", jobName);
    }

    /**
     * 校验是否执行任务，提供给子类覆盖
     * @return
     */
    protected boolean ifExe(String jobName) {
        return true;
    }


    /**
     * 执行真正的、最原子的业务逻辑，不用关心分库分表
     * 特别注意：scheduleContext是非线程安全的，这个参数只为了获取参数打印日志，如需操作，请注意线程安全
     */
    protected abstract void doRealJob() throws Exception;

}
