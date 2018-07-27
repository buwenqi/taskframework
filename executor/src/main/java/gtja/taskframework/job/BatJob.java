package gtja.taskframework.job;

import gtja.taskframework.util.ScriptUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wenqi on 2018/7/26.
 */
public class BatJob implements Job {
    private static Logger logger=LoggerFactory.getLogger(BatJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        //获取传递的java文件信息
        JobDataMap jobDataMap=jobExecutionContext.getJobDetail().getJobDataMap();
        ScriptUtil.executeScript(jobDataMap,logger);
    }


}
