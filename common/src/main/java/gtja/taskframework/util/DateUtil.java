package gtja.taskframework.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wenqi on 2018/7/19.
 */
public class DateUtil {
    private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd H:m:s");

    /**
     * 将date转换成yyyy-MM-dd H:m:s格式的字符串
     * @param date
     * @return
     */
    public static String formatDate(Date date){
        return format.format(date);
    }
}
