package gtja.taskframework.controller;

import gtja.taskframework.util.ExecutorStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wenqi on 2018/7/27.
 */
@RestController
@RequestMapping("/status")
public class ExecutorController {

    /**
     * 查看是否在线，心跳
     * @return
     */
    @GetMapping("/heartbeat")
    public String heartBeat(){
        return ExecutorStatus.ONLINE.val();
    }
}
