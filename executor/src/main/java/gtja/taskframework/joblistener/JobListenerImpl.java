package gtja.taskframework.joblistener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

/**
 * Created by wenqi on 2018/7/27.
 */
@Component
public class JobListenerImpl implements JobListener {
    @Override
    public String getName() {
        return "jobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {

    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        JobKey jobKey=jobExecutionContext.getJobDetail().getKey();
        System.out.println("jobKey:"+jobKey+" was executed!");
    }
}
