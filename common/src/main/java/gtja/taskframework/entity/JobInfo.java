package gtja.taskframework.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wenqi on 2018/7/18.
 */
public class JobInfo implements Serializable {
    /**
     * job基本信息
     */
    @JSONField(ordinal = 1)
    private long id;
    @JSONField(ordinal = 2)
    private String jobName;
    @JSONField(ordinal = 3)
    private String jobGroup;
    @JSONField(ordinal = 4)
    private String jobCron;
    @JSONField(ordinal = 5)
    private String jobDesc;
    /**
     * job状态信息，0代表运行中，1代表已完成，2代表暂停
     */
    @JSONField(ordinal = 11)
    private int jobStatus;
    @JSONField(ordinal = 6)
    private String jobFilePath;
    @JSONField(ordinal = 7)
    private String jobFileType;

    //附加信息
    @JSONField(ordinal = 8)
    private Date addTime;
    @JSONField(ordinal = 9)
    private String author;
    @JSONField(ordinal = 10)
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public int getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(int jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobFilePath() {
        return jobFilePath;
    }

    public void setJobFilePath(String jobFilePath) {
        this.jobFilePath = jobFilePath;
    }

    public String getJobFileType() {
        return jobFileType;
    }

    public void setJobFileType(String jobFileType) {
        this.jobFileType = jobFileType;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("[jobId:%d, jobName:%s, jobGroup:%s, jobCron:%s ]", id, jobName, jobGroup, jobCron);
    }
}
