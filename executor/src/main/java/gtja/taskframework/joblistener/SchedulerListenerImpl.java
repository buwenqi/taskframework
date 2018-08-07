package gtja.taskframework.joblistener;

import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 * Created by wenqi on 2018/7/26.
 */
@Component
public class SchedulerListenerImpl implements SchedulerListener {
    @Override
    public void jobScheduled(Trigger trigger) {
        System.out.println("jobScheduled:"+trigger.getJobKey());

    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        System.out.println("jobUnScheduled:"+triggerKey);
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        System.out.println("triggerFinalized");
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        System.out.println("triggerPaused");
    }

    @Override
    public void triggersPaused(String s) {
        System.out.println("triggerPaused string");
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        System.out.println("triggerResumed");
    }

    @Override
    public void triggersResumed(String s) {
        System.out.println("triggerResumed string");
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        System.out.println(jobDetail.getKey().toString()+"任务被添加");
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        System.out.println(jobKey.toString()+"任务被删除");
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        System.out.println(jobKey.toString()+"任务被暂停");
    }

    @Override
    public void jobsPaused(String s) {
        System.out.println(s+"jobsPaused");
    }

    @Override
    public void jobResumed(JobKey jobKey) {
        System.out.println(jobKey.toString()+"任务被重启");
    }

    @Override
    public void jobsResumed(String s) {
        System.out.println(s+"jobREsumed");
    }

    @Override
    public void schedulerError(String s, SchedulerException e) {

    }

    @Override
    public void schedulerInStandbyMode() {

    }

    @Override
    public void schedulerStarted() {

    }

    @Override
    public void schedulerStarting() {

    }

    @Override
    public void schedulerShutdown() {

    }

    @Override
    public void schedulerShuttingdown() {

    }

    @Override
    public void schedulingDataCleared() {

    }
}
