package gtja.taskframework;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import gtja.taskframework.dao.JobInfoDao;
import gtja.taskframework.entity.JobInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class jsonTest {

    @Autowired
    JobInfoDao jobInfoDao;
    @Test
    public void getJobInfo() {

        List<JobInfo> jobInfo = jobInfoDao.listAllJob();
        int total = jobInfo.size();
        JSONObject result = new JSONObject(true);
        result.put("total", total);
        result.put("rows", jobInfo);

        System.out.println(result.toJSONString());

    }
}