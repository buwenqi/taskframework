package gtja.taskframework.task;

import gtja.taskframework.entity.JobInfo;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;

/**
 * Created by wenqi on 2018/7/19.
 * 用于加载java文件任务
 */
public class JavaJobTask implements Runnable {
    private JobInfo jobInfo;
    private Scheduler scheduler;

    public JavaJobTask(JobInfo jobInfo, Scheduler scheduler) {
        this.jobInfo = jobInfo;
        this.scheduler=scheduler;
    }

    @Override
    public void run() {
        //执行任务
      /*  JobDetail jobDetail=JobBuilder.newJob(getClass().getClass()).*/


    }

}
