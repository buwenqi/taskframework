package gtja.taskframework.util;

/**
 * Created by wenqi on 2018/7/20.
 */
public enum JobStatusEnum {
    RUNNING("running"),REMOVED("removed"),UPDATED("updated"),
    PAUSED("paused"),TRIGGERED("triggered"),EXSISTED("exsisted"),
    NOTEXSISTED("notexsisted");
    private String val;
    private JobStatusEnum(String val){
        this.val=val;
    }

    public String val(){
        return val;
    }
}
