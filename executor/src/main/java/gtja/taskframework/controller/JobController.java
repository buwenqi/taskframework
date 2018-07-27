package gtja.taskframework.controller;

import gtja.taskframework.entity.JobInfo;
import gtja.taskframework.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wenqi on 2018/7/23.
 */
@RestController
@RequestMapping("/executor")
public class JobController {

    @Autowired
    JobService jobService;

    @RequestMapping(value ="/addJob",method = RequestMethod.POST)
    public String addJob(@RequestBody JobInfo jobInfo){
        return jobService.executeJob(jobInfo);
    }

    @RequestMapping(value = "/triggerJob",method=RequestMethod.POST)
    public String triggerJob(@RequestBody JobInfo jobInfo){
        return jobService.triggerJob(jobInfo);
    }

    @RequestMapping(value = "/updateJob",method=RequestMethod.POST)
    public String updateJob(@RequestBody JobInfo jobInfo){
        return jobService.updateJob(jobInfo);
    }

    @RequestMapping(value = "/pauseJob",method=RequestMethod.POST)
    public String pauseJob(@RequestBody JobInfo jobInfo){
        return jobService.pauseJob(jobInfo);
    }

    @RequestMapping(value = "/resumeJob",method=RequestMethod.POST)
    public String resumeJob(@RequestBody JobInfo jobInfo){
        return jobService.resumeJob(jobInfo);
    }

    @RequestMapping(value = "/removeJob",method=RequestMethod.POST)
    public String removeJob(@RequestBody JobInfo jobInfo){
        return jobService.removeJob(jobInfo);
    }


}
