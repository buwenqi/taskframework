package gtja.taskframework.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import gtja.taskframework.dao.ExecutorDao;
import gtja.taskframework.entity.ExecutorInfo;
import gtja.taskframework.util.ExecutorStatus;
import gtja.taskframework.util.ReturnStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by wenqi on 2018/7/27.
 */
@RestController
@RequestMapping("/executor")
public class ExecutorController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ExecutorDao executorDao;

    /**
     * 接收远程executor的自动注册，获取信息
     *
     * @param executorInfo
     * @return
     */
    @PostMapping("/auto_register")
    public String autoRegister(@RequestBody ExecutorInfo executorInfo) {
        //设置executor的状态为连接状态，并插入数据库,如果出现异常应该返回ReturnStatusEnum.Fail

        if (executorInfo != null&&!StringUtils.isEmpty(executorInfo.getIpAddress())&&!StringUtils.isEmpty(executorInfo.getPort())) {
            //将这个executor信息写入数据库

            //System.out.println(executorInfo.getUsername()+":"+executorInfo.getPassword());
            //检查数据库中是否存在
            if(executorDao.findExecutorByIpAndPort(executorInfo.getIpAddress(),executorInfo.getPort())==null) {
                executorInfo.setStatus(1);
                executorDao.saveExecutor(executorInfo);
            }
            return ReturnStatusEnum.SUCCESS.val();
        }else{
            return ReturnStatusEnum.FAILED.val();
        }

    }

    @PostMapping("/heartbeat")
    public String heartBeat(@RequestBody  ExecutorInfo executorInfo){
        String baseUrl="http://"+executorInfo.getIpAddress()+":"+executorInfo.getPort()+"/status/heartbeat";
        ResponseEntity<String> responseEntity=restTemplate.getForEntity(baseUrl,String.class);
        if(responseEntity.getStatusCode()==HttpStatus.OK&&ExecutorStatus.ONLINE.val().equals(responseEntity.getBody())){
            //更新数据库
            //设置在线
            executorInfo.setStatus(1);
            executorDao.updateExecutor(executorInfo);
            //返回成功
            return ExecutorStatus.ONLINE.val();
        }else{
            //更新数据库
            executorInfo.setStatus(0);
            executorDao.updateExecutor(executorInfo);
            //返回失败
            return ExecutorStatus.OFFLINE.val();
        }
    }

    @PostMapping("/add_executor")
    public String addExecutor(ExecutorInfo executorInfo){
        if(executorInfo != null){
            executorInfo.setStatus(0);
            executorDao.saveExecutor(executorInfo);
        }
        JSONObject result = new JSONObject();
        result.put("state", "success");
        return result.toJSONString();
    }

    @PostMapping("/update_executor")
    public String updateExecutor(ExecutorInfo executorInfo){
        executorDao.updateExecutor(executorInfo);
        JSONObject result = new JSONObject();
        result.put("state", "success");
        return result.toJSONString();
    }

    @PostMapping("/remove_executor")
    public String removeExecutor(long id){
        executorDao.deleteExecutor(id);
        JSONObject result = new JSONObject();
        result.put("state", "success");
        return result.toJSONString();
    }

    @PostMapping("/list_executor")
    public String listExecutor(@RequestParam int pageNumber){
        List<ExecutorInfo> executorInfos = executorDao.listAllExecutor();
        int total = executorInfos.size();
        JSONObject result = new JSONObject();
        result.put("total", total);
        result.put("rows", executorInfos);
        return result.toJSONString();
    }

    @RequestMapping("/getOption")
    public String getExecutorOption(){
        List<ExecutorInfo> executorInfos = executorDao.listAllExecutor();
        String result = JSON.toJSONString(executorInfos);
        return result;
    }
}
