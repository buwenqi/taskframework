package gtja.taskframework.job;

import gtja.taskframework.util.ScriptUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by wenqi on 2018/7/26.
 */
public class ShellJob implements Job {
    private Logger logger=LoggerFactory.getLogger(ShellJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取传递的shell文件信息
        JobDataMap jobDataMap=jobExecutionContext.getJobDetail().getJobDataMap();
        ScriptUtil.executeScript(jobDataMap,logger);
    }
}
