package gtja.taskframework.config;

import gtja.taskframework.interceptor.TokenFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wenqi on 2018/7/30.
 */
@Configuration
public class FilterConfig {

    @Value("${server.username}")
    private String username;

    @Value("${server.password}")
    private String password;

    @Bean
    public FilterRegistrationBean myOncePerRequestFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        TokenFilter tokenFilter=new TokenFilter();
        tokenFilter.setUsername(username);
        tokenFilter.setPassword(password);
        registration.setFilter(tokenFilter);
        registration.addUrlPatterns("/executor/*");// 拦截路径
        registration.setName("TokenFilter");// 拦截器名称
        return registration;
    }
}
