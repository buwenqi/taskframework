package gtja.taskframework.util;

/**
 * Created by wenqi on 2018/7/20.
 */
public enum FileTypeEnum {
    JAVA("java"),PYTHON("python"),JAR("jar");

    private String val;
    private FileTypeEnum(String val){
        this.val=val;
    }

    public String val(){
        return val;
    }
}
