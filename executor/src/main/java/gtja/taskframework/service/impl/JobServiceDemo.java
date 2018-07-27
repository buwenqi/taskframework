package gtja.taskframework.service.impl;

import gtja.taskframework.job.JavaJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by wenqi on 2018/7/17.
 */
@Service
public class JobServiceDemo {
    private Logger logger=LoggerFactory.getLogger(JobServiceDemo.class);

    @Autowired
    @Qualifier("scheduler")
    private Scheduler scheduler;

    public void startSchedule(){
        //启动调动器
        try {
            scheduler.start();
            logger.info("日志生成成功");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void addJobTest(){
        long currentTime=System.currentTimeMillis();
        JobDetail jobDetail=JobBuilder.newJob(JavaJob.class).
                withIdentity("job"+currentTime,"test").build();
    }
    public void addJob(String jobClassName, String jobGroupName, String cronExpression ){
        try {
            //任务信息
            long currentTime=System.nanoTime();
            JobDetail jobDetail=JobBuilder.newJob(getClass(jobClassName).getClass()).
                    withIdentity("job"+currentTime,jobGroupName).build();
            //触发器
            CronScheduleBuilder cronScheduleBuilder=CronScheduleBuilder.cronSchedule(cronExpression);
            CronTrigger trigger=TriggerBuilder.newTrigger().withIdentity("testTrigger"+currentTime,"testTriggerGroup2").
                    withSchedule(cronScheduleBuilder).build();
            scheduler.scheduleJob(jobDetail,trigger);
            System.out.println("任务执行成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Job getClass(String classname) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> class1=Class.forName(classname);
        return (Job)class1.newInstance();
    }
}
