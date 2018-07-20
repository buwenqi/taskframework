package gtja.taskframework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * Created by wenqi on 2018/7/19.
 */
@Configuration
public class ThreadPoolConfig {

    @Bean("threadPool")
    public ThreadPoolExecutor getThreadPoolExecutor(){
        int coreSize=Runtime.getRuntime().availableProcessors();
        ExecutorService executorService=Executors.newFixedThreadPool(2);
        return new ThreadPoolExecutor(coreSize*2,coreSize*8,
                3,TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(coreSize*2),Executors.defaultThreadFactory());
    }
}
