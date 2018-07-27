package gtja.taskframework.controller;

import gtja.taskframework.entity.ExecutorInfo;
import gtja.taskframework.util.ExecutorStatus;
import gtja.taskframework.util.ReturnStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

/**
 * Created by wenqi on 2018/7/27.
 */
@RestController
@RequestMapping("/executor")
public class ExecutorController {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 接收远程executor的自动注册，获取信息
     *
     * @param executorInfo
     * @return
     */
    @PostMapping("/auto_register")
    public String autoRegister(@RequestBody ExecutorInfo executorInfo) {
        //设置executor的状态为连接状态，并插入数据库,如果出现异常应该返回ReturnStatusEnum.Fail

        if (executorInfo != null&&StringUtils.isEmpty(executorInfo.getIpAddress())) {
            //将这个executor信息写入数据库

            return ReturnStatusEnum.SUCCESS.val();
        }else{
            return ReturnStatusEnum.FAILED.val();
        }

        //返回成功结果

    }

    @PostMapping("/heartbeat")
    public String heartBeat(@RequestBody ExecutorInfo executorInfo){
        String baseUrl="http://"+executorInfo.getIpAddress()+":"+executorInfo.getPort()+"/executor/heartbeat";
        ResponseEntity<String> responseEntity=restTemplate.getForEntity(baseUrl,String.class);
        if(responseEntity.getStatusCode()==HttpStatus.OK&&ExecutorStatus.ONLINE.val().equals(responseEntity.getBody())){
            //更新数据库

            //返回成功
            return ExecutorStatus.ONLINE.val();
        }else{
            //更新数据库

            //返回失败
            return ExecutorStatus.OFFLINE.val();
        }
    }

    @PostMapping("/add_executor")
    public String addExecutor(ExecutorInfo executorInfo){
        return null;
    }

    @PostMapping("/update_executor")
    public String updateExecutor(ExecutorInfo executorInfo){
        return null;
    }

    @PostMapping("/remove_executor")
    public String removeExecutor(ExecutorInfo executorInfo){
        return null;
    }

    @PostMapping("/list_executor")
    public String listExecutor(ExecutorInfo executorInfo){
        return null;
    }


}