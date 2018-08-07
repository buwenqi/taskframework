package gtja.taskframework.util;

/**
 * Created by wenqi on 2018/7/27.
 */
public enum ExecutorStatus {
    ONLINE("online"),OFFLINE("offline");

    private String val;
    private ExecutorStatus(String val){
        this.val=val;
    }

    public String val(){
        return val;
    }
}
