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
import gtja.taskframework.util.RestTemplateUtil;
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

            //找到对应的executor信息
            ExecutorInfo executorInfo=executorDao.findExecutorById(jobInfo.getExecutorInfo().getId());
            jobInfo.setExecutorInfo(executorInfo);
            //找到executor信息后组合请求格式
            String baseUrl="http://"+executorInfo.getIpAddress()+":"+executorInfo.getPort();
            String requestUrl=baseUrl+"/executor/addJob";
            //用restTemplate请求executor服务
            ResponseEntity<String> responseEntity=null;
            responseEntity = RestTemplateUtil.postForEntity(restTemplate, requestUrl, jobInfo, String.class);
            if(responseEntity==null){
                return ReturnStatusEnum.FAILED.val();
            }
            //请求完成后判断是否请求成功
            if(responseEntity.getStatusCode()==HttpStatus.OK){
                //请求成功后，获取body中的信息
                JSONObject jsonObject=JSON.parseObject(responseEntity.getBody());
                String jobStatus=jsonObject.getString("jobStatus");
                //检查是不是成功状态
                if(JobStatusEnum.RUNNING.val().equals(jobStatus)){
                    jobInfo.setAddTime(new Date());
                    jobInfo.setJobStatus(0);
                    jobInfoDao.saveJobInfo(jobInfo);
                    return ReturnStatusEnum.SUCCESS.val();
                }
            }

        }
        //其他情况返回失败
        return ReturnStatusEnum.FAILED.val();
    }

    @RequestMapping("/deleteJob")
    public String deleteJob(long id){

        JobInfo jobInfo = jobInfoDao.findJobById(id);
        if (jobInfo!=null){
            String baseUrl="http://"+jobInfo.getExecutorInfo().getIpAddress()+":"+jobInfo.getExecutorInfo().getPort();
            String requestUrl=baseUrl+"/executor/removeJob";
            ResponseEntity<String> responseEntity=null;
            responseEntity = RestTemplateUtil.postForEntity(restTemplate, requestUrl, jobInfo, String.class);
            if(responseEntity==null){
                return ReturnStatusEnum.FAILED.val();
            }
            if(responseEntity.getStatusCode()==HttpStatus.OK){
                JSONObject jsonObject = JSON.parseObject(responseEntity.getBody());
                String jobStatus = jsonObject.getString("jobStatus");
                if(JobStatusEnum.REMOVED.val().equals(jobStatus)){
                    jobInfoDao.deleteJob(id);
                    return ReturnStatusEnum.SUCCESS.val();
                }
            }
        }

        return ReturnStatusEnum.FAILED.val();
    }

    @RequestMapping("/updateJob")
    public String updateJob(JobInfo jobInfo) {
        //1.插入本地数据库
        if(jobInfo!=null){
            ExecutorInfo executorInfo = executorDao.findExecutorById(jobInfo.getExecutorInfo().getId());
            jobInfo.setExecutorInfo(executorInfo);
            String baseUrl="http://"+executorInfo.getIpAddress()+":"+executorInfo.getPort();
            String requestUrl=baseUrl+"/executor/updateJob";
            ResponseEntity<String> responseEntity=null;
            responseEntity = RestTemplateUtil.postForEntity(restTemplate, requestUrl, jobInfo, String.class);
            if(responseEntity==null){
                return ReturnStatusEnum.FAILED.val();
            }
            if(responseEntity.getStatusCode()==HttpStatus.OK){
                JSONObject jsonObject = JSON.parseObject(responseEntity.getBody());
                String jobStatus = jsonObject.getString("jobStatus");
                if(JobStatusEnum.UPDATED.val().equals(jobStatus)){
                    jobInfo.setUpdateTime(new Date());
                    jobInfo.setJobStatus(0);
                    jobInfoDao.updateJobInfo(jobInfo);
                    return ReturnStatusEnum.SUCCESS.val();
                }
            }
        }

        return ReturnStatusEnum.FAILED.val();
    }

    @RequestMapping("/pauseJob")
    public String pauseJob(long id){

        JobInfo jobInfo = jobInfoDao.findJobById(id);
        if (jobInfo!=null){
            String baseUrl="http://"+jobInfo.getExecutorInfo().getIpAddress()+":"+jobInfo.getExecutorInfo().getPort();
            String requestUrl=baseUrl+"/executor/pauseJob";
            ResponseEntity<String> responseEntity=null;
            responseEntity = RestTemplateUtil.postForEntity(restTemplate, requestUrl, jobInfo, String.class);
            if(responseEntity==null){
                return ReturnStatusEnum.FAILED.val();
            }
            if(responseEntity.getStatusCode()==HttpStatus.OK){
                JSONObject jsonObject = JSON.parseObject(responseEntity.getBody());
                String jobStatus = jsonObject.getString("jobStatus");

                if(JobStatusEnum.PAUSED.val().equals(jobStatus)){
                    int jobStatu = 2;
                    jobInfoDao.jobStatus(id, jobStatu);
                    return ReturnStatusEnum.SUCCESS.val();
                }
            }
        }
        return ReturnStatusEnum.FAILED.val();

    }

    @RequestMapping("/resumeJob")
    public String resumeJob(long id){

        JobInfo jobInfo = jobInfoDao.findJobById(id);
        if (jobInfo!=null){
            String baseUrl="http://"+jobInfo.getExecutorInfo().getIpAddress()+":"+jobInfo.getExecutorInfo().getPort();
            String requestUrl=baseUrl+"/executor/resumeJob";
            ResponseEntity<String> responseEntity=null;
            responseEntity = RestTemplateUtil.postForEntity(restTemplate, requestUrl, jobInfo, String.class);
            if(responseEntity==null){
                return ReturnStatusEnum.FAILED.val();
            }
            if(responseEntity.getStatusCode()==HttpStatus.OK){
                JSONObject jsonObject = JSON.parseObject(responseEntity.getBody());
                String jobStatus = jsonObject.getString("jobStatus");
                if(JobStatusEnum.RUNNING.val().equals(jobStatus)){
                    int jobStatu = 0;
                    jobInfoDao.jobStatus(id, jobStatu);
                    return ReturnStatusEnum.SUCCESS.val();
                }
            }
        }
        return ReturnStatusEnum.FAILED.val();
    }

    @RequestMapping("/triggerJob")
    public String triggerJob(long id){
        JobInfo jobInfo = jobInfoDao.findJobById(id);
        if (jobInfo!=null){
            String baseUrl="http://"+jobInfo.getExecutorInfo().getIpAddress()+":"+jobInfo.getExecutorInfo().getPort();
            String requestUrl=baseUrl+"/executor/triggerJob";
            ResponseEntity<String> responseEntity=null;
            responseEntity = RestTemplateUtil.postForEntity(restTemplate, requestUrl, jobInfo, String.class);
            if(responseEntity==null){
                return ReturnStatusEnum.FAILED.val();
            }
            if(responseEntity.getStatusCode()==HttpStatus.OK){
                JSONObject jsonObject = JSON.parseObject(responseEntity.getBody());
                String jobStatus = jsonObject.getString("jobStatus");
                if(JobStatusEnum.TRIGGERED.val().equals(jobStatus)){
                    return ReturnStatusEnum.SUCCESS.val();
                }
            }
        }
        return ReturnStatusEnum.FAILED.val();
    }


}
