package gtja.taskframework.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import gtja.taskframework.entity.ExecutorInfo;
import gtja.taskframework.entity.JobInfo;
import gtja.taskframework.util.JobStatusEnum;
import gtja.taskframework.util.ReturnStatusEnum;
import gtja.taskframework.util.ReturnUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by wenqi on 2018/7/27.
 */
public class TokenFilter extends OncePerRequestFilter {

    private String username;

    private String password;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (!(httpServletRequest instanceof CustomServletRequestWrapper)) {
            httpServletRequest = new CustomServletRequestWrapper(httpServletRequest);
        }
        //System.out.println("拦截器开始拦截");
        BufferedReader reader=httpServletRequest.getReader();
        //读出reader中body的数据
        String line=reader.readLine();
        JobInfo jobInfo=JSON.parseObject(line,new TypeReference<JobInfo>(){});
        ExecutorInfo executorInfo=jobInfo.getExecutorInfo();
        //System.out.println(username+":"+password);
        if(executorInfo!=null&&username.equals(executorInfo.getUsername())&&password.equals(executorInfo.getPassword())){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }else{
            httpServletResponse.getOutputStream().println(ReturnUtil.returnMsg(ReturnStatusEnum.SUCCESS, JobStatusEnum.LOGINERROR,"username or password wrong"));
        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
