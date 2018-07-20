package gtja.taskframework.executor.impl;

import gtja.taskframework.entity.JobInfo;
import gtja.taskframework.executor.Executor;
import gtja.taskframework.task.JavaJobTask;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

    private Logger logger=LoggerFactory.getLogger(DefaultExecutor.class);

    @Autowired
    @Qualifier("threadPool")
    private ThreadPoolExecutor threadPool;

    /**
     * 初始化启动调度器
     */
    @PostConstruct
    public void init(){
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            logger.info("[msg:{}]","sheduler初始化失败！");
            e.printStackTrace();
        }
    }

    /**
     * 执行任务，使用线程池进行任务执行
     * @param jobInfo
     */
    @Override
    public void execute(JobInfo jobInfo) {
        //在这里区分执行任务的线程
        threadPool.execute(new JavaJobTask(jobInfo,scheduler));
    }

    /**
     *bean销毁后关闭sheduler
     */
    @PreDestroy
    public void destroy() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            logger.info("[msg:{}]","关闭sheduler出现异常");
            e.printStackTrace();
        }
        threadPool.shutdown();
    }
}
