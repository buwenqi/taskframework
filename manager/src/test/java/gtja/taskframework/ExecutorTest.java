package gtja.taskframework;

import gtja.taskframework.entity.ExecutorInfo;
import gtja.taskframework.entity.JobInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Created by wenqi on 2018/7/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ExecutorTest {
    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl="http://localhost:8081/executor";

    private JobInfo jobInfo;

    @Before
    public void init() {
        jobInfo=new JobInfo();
        jobInfo.setJobName("jobtest2");
        jobInfo.setJobGroup("jobtestGroup2");
        jobInfo.setJobCron("1/10 * * * * ?");
        jobInfo.setJobFilePath("D:\\code\\HelloJob.java");
        jobInfo.setJobDesc("hello job");
        jobInfo.setJobFileType("java");

        ExecutorInfo executorInfo=new ExecutorInfo();
        executorInfo.setId(1L);
        executorInfo.setUsername("admin");
        executorInfo.setPassword("admin");
        jobInfo.setExecutorInfo(executorInfo);
    }

    @Test
    public void postMethodTest(){
        System.out.println(baseUrl+"/removeJob");
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(baseUrl+"/removeJob",jobInfo,String.class);
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }
}
