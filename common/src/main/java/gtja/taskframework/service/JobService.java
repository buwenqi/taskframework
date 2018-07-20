package gtja.taskframework.service;

import gtja.taskframework.entity.JobInfo;

/**
 * Created by wenqi on 2018/7/18.
 */
public interface JobService {
    void executeJob(JobInfo jobInfo);
}
