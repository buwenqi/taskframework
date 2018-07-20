package gtja.taskframework.job;

import groovy.json.internal.IO;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import gtja.taskframework.entity.JobInfo;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by wenqi on 2018/7/17.
 * 用于定义java文件的job
 */
public class JavaJob implements Job {
    private Logger logger=LoggerFactory.getLogger(JavaJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        //获取传递的java文件信息
        JobDataMap jobDataMap=jobExecutionContext.getJobDetail().getJobDataMap();
        String jobFilePath=jobDataMap.getString("jobFilePath");

        //动态加载要执行的文件
        try {
            File file = new File(jobFilePath);
            GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
            Class clazz=groovyClassLoader.parseClass(file);
            GroovyObject groovyObject=(GroovyObject) clazz.newInstance();
            //执行execute方法，无参数，约定必须将待执行代码放在execute方法里面
            groovyObject.invokeMethod("execute",null);
        }catch (IOException e){
            logger.info("java任务文件读入错误！");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.info("java任务实例化失败！");
            e.printStackTrace();
        } catch (InstantiationException e) {
            logger.info("java任务实例化失败！");
            e.printStackTrace();
        }
    }
}
