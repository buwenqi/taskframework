package gtja.taskframework.executor.thread;

import gtja.taskframework.entity.JobInfo;
import gtja.taskframework.job.JavaJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wenqi on 2018/7/19.
 * 用于执行quarz任务
 */
public class JobTaskThread implements Runnable {
    private JobInfo jobInfo;
    private Scheduler scheduler;
    private Class<? extends Job> jobClass;
    private Logger logger;

    public JobTaskThread(JobInfo jobInfo, Scheduler scheduler,Class<? extends Job> jobClass) {
        this.jobInfo = jobInfo;
        this.scheduler=scheduler;
        this.jobClass=jobClass;
        logger=LoggerFactory.getLogger(JobTaskThread.class);
    }

    public JobTaskThread(){}

    @Override
    public void run() {
        //执行任务
        //任务详情
        JobDetail jobDetail=JobBuilder.newJob(jobClass).
                withDescription(jobInfo.getJobDesc()).
                usingJobData("jobFilePath",jobInfo.getJobFilePath()).
                usingJobData("jobName",jobInfo.getJobName()).
                usingJobData("jobGroup",jobInfo.getJobGroup()).
                usingJobData("email",jobInfo.getEmail()).
                withIdentity(jobInfo.getJobName(),jobInfo.getJobGroup()).build();

        //触发器
        CronScheduleBuilder cronScheduleBuilder=CronScheduleBuilder.cronSchedule(jobInfo.getJobCron());
        CronTrigger trigger=TriggerBuilder.newTrigger().
                withIdentity(jobInfo.getJobName()+"-trigger",jobInfo.getJobGroup()+"-trigger-group").
                withSchedule(cronScheduleBuilder).build();

        try {
            scheduler.scheduleJob(jobDetail,trigger);
        } catch (SchedulerException e) {
            logger.info("scheduler出现问题！jobName:{}, jobGroup:{}",jobInfo.getJobName(),jobInfo.getJobGroup());
            e.printStackTrace();
        }

    }

}
