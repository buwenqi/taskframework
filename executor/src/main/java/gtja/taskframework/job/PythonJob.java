package gtja.taskframework.job;

import gtja.taskframework.util.ScriptUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wenqi on 2018/7/26.
 */
public class PythonJob implements Job {
    private static Logger logger=LoggerFactory.getLogger(PythonJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap=jobExecutionContext.getJobDetail().getJobDataMap();
        ScriptUtil.executeScript(jobDataMap,logger,"python ");
    }
}
