package gtja.taskframework.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.*;

/**
 * Created by wenqi on 2018/7/30.
 */
@Component
public class MailUtil {

    @Autowired
    SimpleEmail simpleEmail;

    private Logger logger=LoggerFactory.getLogger(MailUtil.class);

    public boolean sendEmail(String subject,String msg,String to){
        simpleEmail.setSubject(subject);
        try {
            InternetAddress internetAddress=new InternetAddress(to);
            List<InternetAddress> addresses=new ArrayList<>();
            addresses.add(internetAddress);
            simpleEmail.setTo(addresses);
            simpleEmail.setMsg(msg);
            simpleEmail.send();
        } catch (AddressException e) {
            logger.info("邮件地址不符合要求");
            e.printStackTrace();
            return false;
        } catch (EmailException e) {
            logger.info("邮件地址不符合要求");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean sendTestEmail(String subject,String msg,String to){
        System.out.println(String.format("subject:%s,msg:%s,to:%s",subject,msg,to));
        return true;
    }
}
