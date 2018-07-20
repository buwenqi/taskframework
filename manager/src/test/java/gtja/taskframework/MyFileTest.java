package gtja.taskframework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * Created by wenqi on 2018/7/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class MyFileTest {

    @Test
    public void test(){
        File file=new File("test\\test.log");
        System.out.println(file.getAbsolutePath());
    }

}
