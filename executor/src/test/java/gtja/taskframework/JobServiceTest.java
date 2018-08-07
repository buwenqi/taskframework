package gtja.taskframework;

import gtja.taskframework.entity.JobInfo;
import gtja.taskframework.service.JobService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

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
        jobInfo.setJobName("jobtest3");
        jobInfo.setJobGroup("jobtestGroup3");
        jobInfo.setJobCron("1/10 * * * * ?");
        jobInfo.setJobFilePath("D:\\code\\hello.py");
        jobInfo.setJobDesc("python job");
        jobInfo.setJobFileType("python");
        jobInfo.setEmail("wqbu@qq.com");
        System.out.println("jobInfo初始化完成！");
    }

    public void serialize(){
        //JSON.toJSONString()
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
        //Thread.sleep(10000);
        System.out.println(jobService.removeJob(jobInfo));
        System.in.read();
    }
}
