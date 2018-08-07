package gtja.taskframework.util;

import gtja.taskframework.entity.JobInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtil{
    private static final Logger logger=LoggerFactory.getLogger(RestTemplateUtil.class);

    public static <T> ResponseEntity<T> getForEntity(RestTemplate restTemplate,String url,Class<T> responseType){
        ResponseEntity<T> responseEntity=null;
        try {
            responseEntity = restTemplate.getForEntity(url, responseType);
        } catch (Exception e) {
            logger.info("restTemplate链接失败！");
            return null;
        }
        return responseEntity;
    }

    public static <T> ResponseEntity<T> postForEntity(RestTemplate restTemplate, String url, JobInfo jobInfo, Class<T> responseType){
        ResponseEntity<T> responseEntity=null;
        try {
            responseEntity = restTemplate.postForEntity(url, jobInfo, responseType);
        }catch (Exception e){
            logger.info("restTemplate链接失败！");
            return null;
        }
        return responseEntity;
    }
}