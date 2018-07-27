package gtja.taskframework;

import gtja.taskframework.entity.ExecutorInfo;
import gtja.taskframework.util.ReturnStatusEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by wenqi on 2018/7/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =Application.class)
public class ExecutorControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void registerTest() throws IOException {
        String url="http://127.0.0.1:8082/executor/auto_register";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, new ExecutorInfo(), String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK &&
                ReturnStatusEnum.SUCCESS.val().equals(responseEntity.getBody())) {
            System.out.println(":executor注册成功");
        } else {
            System.out.println( ":executor注册成功");
        }
        System.in.read();
    }
}
