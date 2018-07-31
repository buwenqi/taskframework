package gtja.taskframework.util;

/**
 * Created by wenqi on 2018/7/22.
 */
public class ReturnUtil {
    public static String returnMsg(ReturnStatusEnum returnStatus,JobStatusEnum jobStatus,String msg){
        return String.format("{\"returnStatus\":\"%s\",\"jobStatus\":\"%s\",\"msg\":\"%s\"}",returnStatus.val(),jobStatus.val(),msg);
    }

    public static String returnMsg(ReturnStatusEnum returnStatus,JobStatusEnum jobStatus){
        return returnMsg(returnStatus,jobStatus,null);
    }
}
