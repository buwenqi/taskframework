package gtja.taskframework;

import groovy.json.internal.IO;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import gtja.taskframework.entity.JobInfo;
import gtja.taskframework.service.JobService;
import gtja.taskframework.service.JobServiceDemo;
import gtja.taskframework.util.JobStatusEnum;
import gtja.taskframework.util.ReturnUtil;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by wenqi on 2018/7/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class JobServiceTest {
    @Autowired
    JobService jobService;

    JobInfo jobInfo;

    @Before
    public void init() {
        jobInfo=new JobInfo();
        jobInfo.setJobName("jobtest1");
        jobInfo.setJobGroup("jobtestGroup1");
        jobInfo.setJobCron("1/3 * * * * ?");
        jobInfo.setJobFilePath("D:\\code\\HelloJob.java");
        jobInfo.setJobDesc("hello job");
        jobInfo.setJobFileType("java");
        System.out.println("jobInfo初始化完成！");
    }

    @Test
    public void executeJobTest() throws IOException {
        System.out.println(jobService.executeJob(jobInfo));
        System.in.read();
    }

    @Test
    public void updateJobTest() throws IOException {
        jobInfo.setJobCron("1/5 * * * * ?");
        System.out.println(jobService.updateJob(jobInfo));
        System.in.read();
    }

    @Test
    public void pauseJobTest() throws IOException, InterruptedException {
        Thread.sleep(10000);
        System.out.println(jobService.pauseJob(jobInfo));
        System.in.read();
    }

    @Test
    public void resumeJobTest() throws IOException{
        System.out.println(jobService.resumeJob(jobInfo));
        System.in.read();
    }

    @Test
    public void triggerJobTest() throws IOException {
        System.out.println(jobService.triggerJob(jobInfo));
        System.in.read();
    }

    @Test
    public void removeJobTest() throws IOException, InterruptedException {
        Thread.sleep(10000);
        System.out.println(jobService.removeJob(jobInfo));
        System.in.read();
    }
}
