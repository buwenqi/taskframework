package gtja.taskframework.config;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * Created by wenqi on 2018/7/30.
 */

@Configuration
public class EmailConfig {

    @Value("${email.hostName}")
    private String hostName;

    @Value("${email.smtpPort}")
    private int port;

    @Value("${email.from}")
    private String from;

    @Value("${email.auth.username}")
    private String username;

    @Value("${email.auth.password}")
    private String password;

    Logger logger=LoggerFactory.getLogger(EmailConfig.class);

    @Bean
    public SimpleEmail getSimpleEmail(){
        SimpleEmail email=new SimpleEmail();
        email.setHostName(hostName);
        email.setSmtpPort(port);
        email.setAuthentication(username,password);
        try {
            email.setFrom(from);
        } catch (EmailException e) {
            logger.info(from+"：email from出现异常");
            e.printStackTrace();
        }
        return email;
    }

    @Bean("mailThreadPool")
    public ThreadPoolExecutor getThreadPoolExecutor(){
        int coreSize=Runtime.getRuntime().availableProcessors();
        ExecutorService executorService=Executors.newFixedThreadPool(2);
        return new ThreadPoolExecutor(coreSize*2,coreSize*4,
                3,TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(coreSize*2),Executors.defaultThreadFactory());
    }

}
