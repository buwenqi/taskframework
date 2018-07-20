package gtja.taskframework.service;

import com.alibaba.dubbo.config.annotation.Service;
import gtja.taskframework.entity.JobInfo;
import gtja.taskframework.executor.Executor;
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

    @Override
    /**
     * jobInfo包含从远端传来job信息
     */
    public void executeJob(JobInfo jobInfo) {
       executor.execute(jobInfo);
    }
}
