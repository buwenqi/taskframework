package gtja.taskframework.task;

import gtja.taskframework.entity.JobInfo;
import gtja.taskframework.job.JavaJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wenqi on 2018/7/19.
 * 用于加载java文件任务
 */
public class JavaJobTask implements Runnable {
    private JobInfo jobInfo;
    private Scheduler scheduler;
    private Logger logger;

    public JavaJobTask(JobInfo jobInfo, Scheduler scheduler) {
        this.jobInfo = jobInfo;
        this.scheduler=scheduler;
        logger=LoggerFactory.getLogger(JavaJobTask.class);
    }

    @Override
    public void run() {
        //执行任务
        //任务详情
        JobDetail jobDetail=JobBuilder.newJob(JavaJob.class).
                withDescription(jobInfo.getJobDesc()).
                usingJobData("jobFilePath",jobInfo.getJobFilePath()).
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
