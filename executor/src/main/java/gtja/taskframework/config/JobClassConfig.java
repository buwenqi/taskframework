package gtja.taskframework.config;

import gtja.taskframework.job.BatJob;
import gtja.taskframework.job.JavaJob;
import gtja.taskframework.job.PythonJob;
import gtja.taskframework.job.ShellJob;
import gtja.taskframework.util.FileTypeEnum;
import org.quartz.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenqi on 2018/7/26.
 */
@Configuration
public class JobClassConfig {

    @Bean("jobClassMap")
    public Map<String,Class<? extends Job>> getJobClassMap(){
        Map<String,Class<? extends Job>> jobClassMap=new HashMap<>();
        jobClassMap.put(FileTypeEnum.JAVA.val(),JavaJob.class);
        jobClassMap.put(FileTypeEnum.PYTHON.val(),PythonJob.class);
        jobClassMap.put(FileTypeEnum.SHELL.val(),ShellJob.class);
        jobClassMap.put(FileTypeEnum.BAT.val(),BatJob.class);
        return jobClassMap;
    }
}
