package gtja.taskframework.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import gtja.taskframework.entity.JobInfo;
import gtja.taskframework.service.JobService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;


/**
 * Created by wenqi on 2018/7/17.
 */
@RestController
@RequestMapping("/job")
public class JobController {

    /**
     * dubbo远程服务消费者
     */
    @Reference(retries = 0,timeout = 3000)
    JobService jobService;

    @RequestMapping("/addJob")
    public String addJob() {
        //1.插入本地数据库

        //2.提交给远程executor执行任务
        return "Success";
    }

    @RequestMapping("/deleteJob")
    public String delteJob(){

        return "Success";
    }
}
