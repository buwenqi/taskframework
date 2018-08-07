package gtja.taskframework;

import gtja.taskframework.dao.ExecutorDao;
import gtja.taskframework.entity.ExecutorInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wenqi on 2018/7/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ExecutorDaoTest {

    @Autowired
    private ExecutorDao executorDao;

    @Test
    public void findTest(){
        ExecutorInfo executorInfo=executorDao.findExecutorByIpAndPort("127.0.0.1",8082);
        System.out.println(executorInfo);
    }
}
