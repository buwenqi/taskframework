package gtja.taskframework.service;

import gtja.taskframework.entity.JobInfo;

/**
 * Created by wenqi on 2018/7/18.
 */
public interface JobService {
    String executeJob(JobInfo jobInfo);

    String triggerJob(JobInfo jobInfo);

    String updateJob(JobInfo jobInfo);

    String pauseJob(JobInfo jobInfo);

    String resumeJob(JobInfo jobInfo);

    String removeJob(JobInfo jobInfo);
}
