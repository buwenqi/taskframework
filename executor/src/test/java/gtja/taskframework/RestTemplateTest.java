package gtja.taskframework;

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
 * Created by wenqi on 2018/7/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RestTemplateTest {
    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl="http://localhost:8082/job";

    private JobInfo jobInfo;

    @Before
    public void init() {
        jobInfo=new JobInfo();
        jobInfo.setJobName("jobtest2");
        jobInfo.setJobGroup("jobtestGroup2");
        jobInfo.setJobCron("1/3 * * * * ?");
        jobInfo.setJobFilePath("D:\\code\\HelloJob.java");
        jobInfo.setJobDesc("hello job");
        jobInfo.setJobFileType("java");
        System.out.println("jobInfo初始化完成！");
    }

    @Test
    public void getMethodTest(){
        ResponseEntity<String> responseEntity=restTemplate.getForEntity(baseUrl+"/deleteJob?id={id}",String.class,1);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void postMethodTest(){
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(baseUrl+"/addJob",jobInfo,String.class);
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }
}
