package gtja.taskframework.config;

import gtja.taskframework.entity.ExecutorInfo;
import gtja.taskframework.executor.Executor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by wenqi on 2018/7/27.
 */
@Configuration
public class ExecutorInfoConfig {

    @Value("${server.port}")
    private Integer port;

    @Value("${server.username}")
    private String username;

    @Value("${server.password}")
    private String password;

    @Value("${server.description}")
    private String description;

    @Bean
    public ExecutorInfo getExecutorBean(){
        ExecutorInfo executorInfo=new ExecutorInfo();
        try {
            String ipAddress=InetAddress.getLocalHost().getHostAddress();
            executorInfo.setIpAddress(ipAddress);
            executorInfo.setPort(port);
            executorInfo.setUsername(username);
            executorInfo.setPassword(password);
            executorInfo.setDescription(description);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
        return executorInfo;
    }
}
