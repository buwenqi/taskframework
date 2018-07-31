package gtja.taskframework.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import gtja.taskframework.dao.ExecutorDao;
import gtja.taskframework.dao.JobInfoDao;
import gtja.taskframework.entity.ExecutorInfo;
import gtja.taskframework.entity.JobInfo;
import gtja.taskframework.util.JobStatusEnum;
import gtja.taskframework.util.ReturnStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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

    @Autowired
    JobInfoDao jobInfoDao;

    @Autowired
    ExecutorDao executorDao;

    @Autowired
    RestTemplate restTemplate;


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
        if(jobInfo!=null) {
            jobInfo.setAddTime(new Date());
            jobInfo.setJobStatus(1);
            jobInfoDao.saveJobInfo(jobInfo);
            //找到对应的executor信息
            ExecutorInfo executorInfo=executorDao.findExecutorById(jobInfo.getExecutorInfo().getId());
            jobInfo.setExecutorInfo(executorInfo);
            //找到executor信息后组合请求格式
            String baseUrl="http://"+executorInfo.getIpAddress()+":"+executorInfo.getPort();
            String requestUrl=baseUrl+"/executor/addJob";
            //用restTemplate请求executor服务
            ResponseEntity<String> responseEntity=restTemplate.postForEntity(requestUrl,jobInfo,String.class);
            //请求完成后判断是否请求成功
            if(responseEntity.getStatusCode()==HttpStatus.OK){
                //请求成功后，获取body中的信息
                JSONObject jsonObject=JSON.parseObject(responseEntity.getBody());
                String jobStatus=jsonObject.getString("jobStatus");
                //检查是不是成功状态
                if(JobStatusEnum.RUNNING.val().equals(jobStatus)){
                    JSONObject result = new JSONObject();
                    result.put("state", ReturnStatusEnum.SUCCESS.val());
                    return result.toJSONString();
                }
            }

        }
        //其他情况返回失败
        JSONObject result = new JSONObject();
        result.put("state", ReturnStatusEnum.FAILED.val());
        return result.toJSONString();
    }

    @RequestMapping("/deleteJob")
    public String deleteJob(long id){
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
