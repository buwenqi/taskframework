package gtja.taskframework.executor;

import gtja.taskframework.entity.JobInfo;

/**
 * Created by wenqi on 2018/7/19.
 */
public interface Executor {
    /**
     * 用于执行任务
     * @param jobInfo
     */
    public String execute(JobInfo jobInfo);

    /**
     * 立即执行一次任务
     * @param jobInfo
     */
    public String executeOnce(JobInfo jobInfo);

    /**
     * 更新一个现有的任务
     * @param jobInfo
     * @return
     */
    public String updateJob(JobInfo jobInfo);

    /**
     * 暂停一个现有的任务
     * @param jobInfo
     * @return
     */
    public String pauseJob(JobInfo jobInfo);

    /**
     * 重启一个现有的任务
     * @param jobInfo
     * @return
     */
    public String resumeJob(JobInfo jobInfo);

    /**
     * 删除一个现有的任务
     * @param jobInfo
     * @return
     */
    public String removeJob(JobInfo jobInfo);
}
