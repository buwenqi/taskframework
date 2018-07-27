package gtja.taskframework.executor.impl;

import gtja.taskframework.entity.JobInfo;
import gtja.taskframework.executor.Executor;
import gtja.taskframework.executor.thread.JobTaskThread;
import gtja.taskframework.job.JavaJob;
import gtja.taskframework.util.JobStatusEnum;
import gtja.taskframework.util.ReturnStatusEnum;
import gtja.taskframework.util.ReturnUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by wenqi on 2018/7/19.
 * 默认的执行器
 */
@Component
public class DefaultExecutor implements Executor {

    @Autowired
    @Qualifier("scheduler")
    private Scheduler scheduler;

    private Logger logger = LoggerFactory.getLogger(DefaultExecutor.class);

    @Autowired
    @Qualifier("jobClassMap")
    private Map<String, Class<? extends Job>> jobClassMap;

    @Autowired
    @Qualifier("threadPool")
    private ThreadPoolExecutor threadPool;

    @Autowired
    JobListener jobListener;

    /**
     * 初始化启动调度器
     */
    @PostConstruct
    public void init() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            logger.info("[msg:{}]", "sheduler初始化失败！");
            e.printStackTrace();
        }

        try {
            scheduler.getListenerManager().addJobListener(jobListener);
        } catch (SchedulerException e) {
            logger.info("msg:{}", "sheduler添加监听器失败！");
            e.printStackTrace();
        }


    }

    /**
     * 执行任务，使用线程池进行任务执行
     *
     * @param jobInfo
     */
    @Override
    public String execute(JobInfo jobInfo) {
        //首先检查任务是否已经存在，jobkey=jobName+jobGroup,如果存储，直接返回EXSISTED
        try {
            if (scheduler.checkExists(getJobKey(jobInfo))) {
                return ReturnUtil.returnMsg(ReturnStatusEnum.SUCCESS, JobStatusEnum.EXSISTED);
            }
        } catch (SchedulerException e) {
            logger.info("checkExists出错");
            e.printStackTrace();
            //异常情况都返回REturnStatus为Fail
            return ReturnUtil.returnMsg(ReturnStatusEnum.FAILED, JobStatusEnum.RUNNING);
        }

        //在这里区分执行任务的线程,根据jobType做策略区分
        Class<? extends Job> jobClass = jobClassMap.get(jobInfo.getJobFileType());
        threadPool.execute(new JobTaskThread(jobInfo, scheduler, jobClass));
        return ReturnUtil.returnMsg(ReturnStatusEnum.SUCCESS, JobStatusEnum.RUNNING);
    }

    @Override
    public String executeOnce(JobInfo jobInfo) {
        //任务如果不存在，返回NOTEXSISTED
        try {
            if (!scheduler.checkExists(getJobKey(jobInfo))) {
                return ReturnUtil.returnMsg(ReturnStatusEnum.SUCCESS, JobStatusEnum.NOTEXSISTED);
            }
        } catch (SchedulerException e) {
            logger.info("executeOnce的checkExists出错");
            e.printStackTrace();
            return ReturnUtil.returnMsg(ReturnStatusEnum.FAILED, JobStatusEnum.RUNNING);
        }


        try {
            scheduler.triggerJob(getJobKey(jobInfo));
        } catch (SchedulerException e) {
            logger.info("触发任务出现错误！");
            e.printStackTrace();
            return ReturnUtil.returnMsg(ReturnStatusEnum.FAILED, JobStatusEnum.RUNNING);
        }
        return ReturnUtil.returnMsg(ReturnStatusEnum.SUCCESS, JobStatusEnum.TRIGGERED);
    }

    @Override
    public String updateJob(JobInfo jobInfo) {
        try {
            if (!scheduler.checkExists(getTriggerKey(jobInfo))) {
                return ReturnUtil.returnMsg(ReturnStatusEnum.SUCCESS, JobStatusEnum.NOTEXSISTED);
            }
        } catch (SchedulerException e) {
            logger.info("对应的触发器不存在！");
            e.printStackTrace();
            return ReturnUtil.returnMsg(ReturnStatusEnum.FAILED, JobStatusEnum.RUNNING);
        }

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getJobCron());
        CronTrigger trigger = TriggerBuilder.newTrigger().
                withIdentity(jobInfo.getJobName() + "-trigger", jobInfo.getJobGroup() + "-trigger-group").
                withSchedule(cronScheduleBuilder).build();
        try {
            scheduler.rescheduleJob(getTriggerKey(jobInfo), trigger);
        } catch (SchedulerException e) {
            logger.info("触发器更新失败");
            e.printStackTrace();
            return ReturnUtil.returnMsg(ReturnStatusEnum.FAILED, JobStatusEnum.RUNNING);
        }
        return ReturnUtil.returnMsg(ReturnStatusEnum.SUCCESS, JobStatusEnum.UPDATED);
    }

    @Override
    public String pauseJob(JobInfo jobInfo) {
        try {
            scheduler.pauseJob(getJobKey(jobInfo));
        } catch (SchedulerException e) {
            logger.info("暂停任务失败！");
            e.printStackTrace();
            return ReturnUtil.returnMsg(ReturnStatusEnum.FAILED, JobStatusEnum.RUNNING);
        }
        return ReturnUtil.returnMsg(ReturnStatusEnum.SUCCESS, JobStatusEnum.PAUSED);
    }

    @Override
    public String resumeJob(JobInfo jobInfo) {
        try {
            scheduler.resumeJob(getJobKey(jobInfo));
        } catch (SchedulerException e) {
            logger.info("重启任务失败！");
            e.printStackTrace();
            return ReturnUtil.returnMsg(ReturnStatusEnum.FAILED, JobStatusEnum.RUNNING);
        }
        return ReturnUtil.returnMsg(ReturnStatusEnum.SUCCESS, JobStatusEnum.RUNNING);
    }

    @Override
    public String removeJob(JobInfo jobInfo) {
        try {
            scheduler.deleteJob(getJobKey(jobInfo));
        } catch (SchedulerException e) {
            logger.info("删除任务失败！");
            e.printStackTrace();
            return ReturnUtil.returnMsg(ReturnStatusEnum.FAILED, JobStatusEnum.RUNNING);
        }
        return ReturnUtil.returnMsg(ReturnStatusEnum.SUCCESS, JobStatusEnum.REMOVED);
    }

    public JobKey getJobKey(JobInfo jobInfo) {
        return JobKey.jobKey(jobInfo.getJobName(), jobInfo.getJobGroup());
    }

    public TriggerKey getTriggerKey(JobInfo jobInfo) {
        return TriggerKey.triggerKey(jobInfo.getJobName() + "-trigger", jobInfo.getJobGroup() + "-trigger-group");
    }

    /**
     * bean销毁后关闭sheduler
     */
    @PreDestroy
    public void destroy() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            logger.info("[msg:{}]", "关闭sheduler出现异常");
            e.printStackTrace();
        }
        threadPool.shutdown();
    }
}
