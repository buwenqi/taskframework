package gtja.taskframework;

import gtja.taskframework.util.MailUtil;
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
public class MailTest {

    @Autowired
    private MailUtil mailUtil;

    @Test
    public void mailTest(){
        boolean result=mailUtil.sendEmail("test","test","wqbu@qq.com");
        if(result){
            System.out.println("发送成功");
        }else{
            System.out.println("发送失败");
        }
    }
}
