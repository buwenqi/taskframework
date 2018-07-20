package gtja.taskframework;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by wenqi on 2018/7/16.
 */
@SpringBootApplication
@EnableDubboConfiguration
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
