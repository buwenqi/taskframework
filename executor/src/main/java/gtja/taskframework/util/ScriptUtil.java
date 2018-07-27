package gtja.taskframework.util;

import org.quartz.JobDataMap;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by wenqi on 2018/7/26.
 */
public class ScriptUtil {

    public static void executeScript(JobDataMap jobDataMap, Logger logger) {
        executeScript(jobDataMap, logger, null);
    }

    public static void executeScript(JobDataMap jobDataMap, Logger logger, String cmdPrefix) {
        String jobFilePath = jobDataMap.getString("jobFilePath");
        String jobName = jobDataMap.getString("jobName");
        String jobGroup = jobDataMap.getString("jobGroup");

        //启动进程运行脚本
        Runtime rt = Runtime.getRuntime();
        Process ps = null;
        try {
            if (cmdPrefix != null) {
                ps = rt.exec(cmdPrefix + jobFilePath);
            } else {
                ps = rt.exec(jobFilePath);
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            String line;
            //读取运行日志
            while ((line = bufferedReader.readLine()) != null) {
                logger.info("jobName:{},jobGroup:{},jobFile:{},msg:{}", jobName, jobGroup, jobFilePath, line);
            }
            int returnValue = ps.waitFor();
            if (returnValue == 0) {
                logger.info("jobName:{},jobGroup:{},jobFile:{},msg:{}", jobName, jobGroup, jobFilePath, "script is successful executed!");
            } else {
                logger.info("jobName:{},jobGroup:{},jobFile:{},msg:{}", jobName, jobGroup, jobFilePath, "script failed!");
            }
        } catch (Exception e) {
            logger.info("jobName:{},jobGroup:{},jobFile:{},msg:{}", jobName, jobGroup, jobFilePath, "account exeception!");
            e.printStackTrace();
        }
    }
}
