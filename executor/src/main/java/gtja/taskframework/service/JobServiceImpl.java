package gtja.taskframework.service;

import com.alibaba.dubbo.config.annotation.Service;
import gtja.taskframework.entity.JobInfo;
import gtja.taskframework.executor.Executor;
import gtja.taskframework.util.JobStatusEnum;
import gtja.taskframework.util.ReturnStatusEnum;
import gtja.taskframework.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wenqi on 2018/7/18.
 * JobService的Provider
 */
@Service(interfaceClass = JobService.class, retries=0,timeout = 3000)
@Component
public class JobServiceImpl implements JobService{

    @Autowired
    Executor executor;

    /**
     * 执行任务
     * jobInfo包含从远端传来job信息
     */
    @Override
    public String executeJob(JobInfo jobInfo) {
       return executor.execute(jobInfo);
    }

    /**
     * 立即触发job,执行一次job任务
     * @param jobInfo
     * @return
     */
    @Override
    public String triggerJob(JobInfo jobInfo) {
        return executor.executeOnce(jobInfo);
    }

    /**
     * 更新现有的job
     * @param jobInfo
     * @return
     */
    @Override
    public String updateJob(JobInfo jobInfo) {
        return executor.updateJob(jobInfo);
    }

    /**
     * 暂停现有的job
     * @param jobInfo
     * @return
     */
    @Override
    public String pauseJob(JobInfo jobInfo) {
        return executor.pauseJob(jobInfo);
    }

    /**
     * 重启暂停的job
     * @param jobInfo
     * @return
     */
    @Override
    public String resumeJob(JobInfo jobInfo) {
        return executor.resumeJob(jobInfo);
    }

    /**
     * 删除已有的job
     * @param jobInfo
     * @return
     */
    @Override
    public String removeJob(JobInfo jobInfo) {
        return executor.removeJob(jobInfo);
    }

}
