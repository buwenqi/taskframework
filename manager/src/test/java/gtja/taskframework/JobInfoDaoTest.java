package gtja.taskframework;

import gtja.taskframework.dao.JobInfoDao;
import gtja.taskframework.entity.ExecutorInfo;
import gtja.taskframework.entity.JobInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by wenqi on 2018/7/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class JobInfoDaoTest {
    @Autowired
    JobInfoDao jobInfoDao;

    @Test
    public void insertTest(){
        JobInfo jobInfo=new JobInfo();
        jobInfo.setJobName("test");
        jobInfo.setJobGroup("testGroup");
        jobInfo.setJobCron("* * 1 * * ? *");
        jobInfo.setJobFilePath("D://job");
        jobInfo.setAddTime(new Date());
        jobInfo.setUpdateTime(new Date());
        jobInfo.setAuthor("wenqi");
        jobInfo.setEmail("1243@qq.com");
        jobInfo.setJobStatus(1);
        ExecutorInfo executorInfo=new ExecutorInfo();
        executorInfo.setIpAddress("127.0.0.1");
        executorInfo.setPort(8081);
        executorInfo.setId(1);
        jobInfo.setExecutorInfo(executorInfo);
        jobInfoDao.saveJobInfo(jobInfo);
    }
}
