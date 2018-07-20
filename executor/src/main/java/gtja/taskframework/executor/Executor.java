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
    public void execute(JobInfo jobInfo);
}
