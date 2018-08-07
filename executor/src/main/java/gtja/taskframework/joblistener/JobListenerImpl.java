package gtja.taskframework.joblistener;

import gtja.taskframework.util.MailUtil;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by wenqi on 2018/7/27.
 */
@Component
public class JobListenerImpl implements JobListener {

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    @Qualifier("mailThreadPool")
    private ThreadPoolExecutor mailThreadPool;

    @Override
    public String getName() {
        return "jobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {

    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        JobKey jobKey=jobExecutionContext.getJobDetail().getKey();
        JobDataMap jobDataMap=jobExecutionContext.getJobDetail().getJobDataMap();
        String email=jobDataMap.getString("email");

        //发送邮件到用户邮箱
        mailThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                String subject=jobKey.toString()+" has been executed once sucessfully!";
                String msg=jobKey.toString()+" has been executed once sucessfully! More info can see logs.";
                //真正发邮件的时候可以改变成sendEmail方法
                mailUtil.sendTestEmail(subject,msg,email);
            }
        });

    }
}
