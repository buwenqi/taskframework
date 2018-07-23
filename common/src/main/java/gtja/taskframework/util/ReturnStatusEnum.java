package gtja.taskframework.util;

/**
 * Created by wenqi on 2018/7/22.
 */
public enum ReturnStatusEnum {
    SUCCESS("success"),FAILED("failed");

    private String val;
    private ReturnStatusEnum(String val){
        this.val=val;
    }

    public String val(){
        return val;
    }
}
