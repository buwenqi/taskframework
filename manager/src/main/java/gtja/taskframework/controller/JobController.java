package gtja.taskframework.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import gtja.taskframework.dao.JobInfoDao;
import gtja.taskframework.entity.JobInfo;
import gtja.taskframework.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


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
    @Autowired
    JobInfoDao jobInfoDao;

    @RequestMapping("/jobInfo")
    public String getJobInfo(@RequestParam int pageNumber,int pageSize,HttpServletResponse response){
        response.setContentType("text/json");
        response.setCharacterEncoding("utf-8");
        List<JobInfo> jobInfo = jobInfoDao.listAllJob();
        int total = jobInfo.size();
        //PageHelper.startPage(pageNumber,pageSize);
        JSONObject result = new JSONObject();
        result.put("total", total);
        result.put("rows", jobInfo);
        System.out.println(result.toJSONString());
        return result.toJSONString();
    }

    @RequestMapping(method = RequestMethod.POST,value = "/uploadFile")
    public String upload(@RequestParam("file")MultipartFile file) throws IOException {
        String path = "D:/job/" + file.getOriginalFilename();
        File newFile = new File(path);
        file.transferTo(newFile);
        JSONObject json = new JSONObject();
        json.put("pathUrl", path);
        System.out.println(1);
        return json.toJSONString();
    }

    @RequestMapping("/addJob")
    public String addJob(JobInfo jobInfo) {
        //1.插入本地数据库
        jobInfo.setAddTime(new Date());
        jobInfo.setJobStatus(1);
        jobInfoDao.saveJobInfo(jobInfo);
        JSONObject result = new JSONObject();
        result.put("state", "success");

        //2.提交给远程executor执行任务
        return result.toJSONString();
    }

    @RequestMapping("/deleteJob")
    public String delteJob(long id){
        jobInfoDao.deleteJob(id);
        JSONObject result = new JSONObject();
        result.put("state", "success");
        return result.toJSONString();
    }

    @RequestMapping("/updateJob")
    public String updateJob(JobInfo jobInfo) {
        //1.插入本地数据库
        jobInfo.setAddTime(new Date());
        jobInfo.setJobStatus(1);
        jobInfoDao.updateJobInfo(jobInfo);
        JSONObject result = new JSONObject();
        result.put("state", "success");

        //2.提交给远程executor执行任务
        return result.toJSONString();
    }

    @RequestMapping("/pauseJob")
    public String pauseJob(long id){
        int jobStatus = 2;
        jobInfoDao.jobStatus(id, jobStatus);
        JSONObject result = new JSONObject();
        result.put("state", "success");
        return result.toJSONString();
    }

    @RequestMapping("/resumeJob")
    public String resumeJob(long id){
        int jobStatus = 0;
        jobInfoDao.jobStatus(id, jobStatus);
        JSONObject result = new JSONObject();
        result.put("state", "success");
        return result.toJSONString();
    }
}
