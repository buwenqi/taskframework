package gtja.taskframework.mapper;

import gtja.taskframework.entity.JobInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.sql.JDBCType;
import java.util.Date;
import java.util.List;

/**
 * Created by wenqi on 2018/7/20.
 */
@Component
public interface JobInfoMapper {

    @Insert("insert into self_jobinfo(job_name,job_group,job_cron,job_desc,job_filepath,job_filetype,add_time,author,email,job_status)" +
            "values(#{jobName},#{jobGroup},#{jobCron},#{jobDesc},#{jobFilePath},#{jobFileType},#{addTime},#{author},#{email},#{jobStatus})")
    @Options(useGeneratedKeys = true, keyColumn = "id",keyProperty = "id")
    void insert(JobInfo jobInfo);

    @Update("update self_jobinfo set job_name=#{jobName},job_group=#{jobGroup},job_cron=#{jonCron},job_desc=#{jobDesc}," +
            "job_filepath=#{jobFilePath},job_filetype=#{jobFileType},add_time=#{addTime},author=#{author},email=#{email}," +
            "job_status=#{jobStatus} where id=#{id}")
    void update(JobInfo jobInfo);

    @Delete("delete from self_jobinfo where id=#{id}")
    void delete(long id);

    @Select("select * from self_jobinfo where job_name=#{jobName}")
    @Results({
            @Result(property = "jobName", column = "job_name"),
            @Result(property = "jobGroup", column = "job_group"),
            @Result(property = "jobCron", column = "job_cron"),
            @Result(property = "jobDesc", column = "job_desc"),
            @Result(property = "jobFilePath", column = "job_filepath"),
            @Result(property = "jobFileType", column = "job_filetype"),
            @Result(property = "addTime", column = "add_time", jdbcType = JdbcType.DATE),
            @Result(property = "jobStatus",column = "job_status")
    })
    JobInfo selectByJobName(String jobName);

    @Select("select * from self_jobinfo")
    @Results({
            @Result(property = "jobName", column = "job_name"),
            @Result(property = "jobGroup", column = "job_group"),
            @Result(property = "jobCron", column = "job_cron"),
            @Result(property = "jobDesc", column = "job_desc"),
            @Result(property = "jobFilePath", column = "job_filepath"),
            @Result(property = "jobFileType", column = "job_filetype"),
            @Result(property = "addTime", column = "add_time", jdbcType = JdbcType.DATE),
            @Result(property = "jobStatus",column = "job_status")
    })
    List<JobInfo> selectAll();

}
