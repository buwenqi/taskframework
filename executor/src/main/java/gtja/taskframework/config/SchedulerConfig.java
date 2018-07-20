package gtja.taskframework.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by wenqi on 2018/7/17.
 */
@Configuration
public class SchedulerConfig {

    @Bean
    public Properties quartzProperties(){
        try {
            PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
            propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
            //在quartz.properties中的属性被读取并注入后再初始化对象
            propertiesFactoryBean.afterPropertiesSet();
            return propertiesFactoryBean.getObject();
        }catch (IOException e){
            System.out.println("读入quartz配置文件错误");
            e.printStackTrace();
        }
        return null;
    }

    @Bean("schedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    /**
     * 注入调度器Bean
     * @return
     */
    @Bean("scheduler")
    public Scheduler scheduler(){
        return schedulerFactoryBean().getScheduler();
    }

    /**
     * quartz初始化监听器
     */
    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }

}
