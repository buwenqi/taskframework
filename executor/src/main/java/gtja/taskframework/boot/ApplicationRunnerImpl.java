package gtja.taskframework.boot;

import gtja.taskframework.entity.ExecutorInfo;
import gtja.taskframework.executor.Executor;
import gtja.taskframework.util.ReturnStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by wenqi on 2018/7/27.
 */
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Autowired
    private ExecutorInfo executorInfo;

    @Value("${manager.ipaddress}")
    private String managerIp;

    @Value("${manager.port}")
    private String managerPort;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    private static Logger logger=LoggerFactory.getLogger(ApplicationRunnerImpl.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //将executorInfo发送到manager
        threadPoolExecutor.execute(() -> {
            String baseUrl = "http://" + managerIp + ":" + managerPort;
            String autoRegisterAddress = "/executor/auto_register";
            System.out.println(baseUrl+autoRegisterAddress);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl + autoRegisterAddress, executorInfo, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK &&
                    ReturnStatusEnum.SUCCESS.val().equals(responseEntity.getBody())) {
                logger.info(executorInfo.getIpAddress() + ":executor注册成功");
            } else {
                logger.info(executorInfo.getIpAddress() + ":executor注册成功");
            }
        });
    }
}
