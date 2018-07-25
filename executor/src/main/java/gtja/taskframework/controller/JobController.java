package gtja.taskframework.controller;

import gtja.taskframework.entity.JobInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wenqi on 2018/7/23.
 */
@RestController
@RequestMapping("/executor")
public class JobController {

    @RequestMapping("/addJob")
    public String addJob(@RequestBody JobInfo jobInfo){
        System.out.println(jobInfo.getJobName());
        return "Success";
    }
}
