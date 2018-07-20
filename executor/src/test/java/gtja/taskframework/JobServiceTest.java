package gtja.taskframework;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import gtja.taskframework.service.JobService;
import gtja.taskframework.service.JobServiceDemo;
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

/**
 * Created by wenqi on 2018/7/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = Application.class)
public class JobServiceTest {
  /*  @Autowired
    JobService jobService;*/

    @Test
    public void test() throws IOException, IllegalAccessException, InstantiationException {
        GroovyClassLoader loader=new GroovyClassLoader();
        Class clazz=loader.parseClass(new File("src\\main\\java\\gtja\\taskframework\\job\\Test.java"));
        GroovyObject object=(GroovyObject) clazz.newInstance();
        Object result=object.invokeMethod("say",new Object[]{});
    }
}
