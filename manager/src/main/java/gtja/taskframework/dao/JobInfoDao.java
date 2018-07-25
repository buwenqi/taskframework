package gtja.taskframework.dao;

import gtja.taskframework.entity.JobInfo;
import gtja.taskframework.mapper.JobInfoMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wenqi on 2018/7/18.
 */
@Repository
public class JobInfoDao {
    @Autowired
    JobInfoMapper jobInfoMapper;

    /**
     * 增加一个job信息
     * @param jobInfo
     */
    public void saveJobInfo(JobInfo jobInfo){
        jobInfoMapper.insert(jobInfo);
    }

    public void updateJobInfo(JobInfo jobInfo){
        jobInfoMapper.update(jobInfo);
    }

    public void deleteJob(long id){
        jobInfoMapper.delete(id);
    }

    public JobInfo findJobByJobName(String jobName){
        return jobInfoMapper.selectByJobName(jobName);
    }

    public List<JobInfo> listAllJob(){
        return jobInfoMapper.selectAll();
    }

    public void jobStatus( long id,  int jobStatus) {jobInfoMapper.updateJobStatus(id, jobStatus);}
}
