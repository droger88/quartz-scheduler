package com.demo.scheduler.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.scheduler.job.JobFactory;
import com.demo.scheduler.job.TriggerFactory;
import com.demo.scheduler.pojo.JobInfo;
import com.demo.scheduler.pojo.ScheduleRequest;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/scheduler")
@Slf4j
public class Controller {

    @Autowired
    private Scheduler scheduler;

    @PostMapping("/{jobName}")
    public ResponseEntity<Object> schedule(@PathVariable String jobName, @RequestBody ScheduleRequest scheduleRequest) {
        JobDetail job = new JobFactory()
                .setDescription(scheduleRequest.getJobDescription())
                .setJobName(jobName)
                .setJobData(scheduleRequest.getData())
                .setJobType(scheduleRequest.getJobType()).build();
        Trigger trigger = new TriggerFactory()
                .setCron(scheduleRequest.getCron())
                .setJobDetail(job)
                .build();
        try {
            Date result = scheduler.scheduleJob(job, trigger);
            Map<String, Object> response = new HashMap();
            response.put("scheduled", result);
            return ResponseEntity.ok().body(response);
        } catch (SchedulerException e) {
            log.error("something went wrong", e);
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/{jobName}")
    public ResponseEntity<Object> patch(@PathVariable String jobName, @RequestBody ScheduleRequest scheduleRequest) {
        deleteJob(jobName);
        return schedule(jobName,scheduleRequest);
    }

    @GetMapping("/{jobName}")
    public ResponseEntity<Object> getJob(@PathVariable String jobName) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName));
            Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey("trigger_" + jobName));
            return ResponseEntity.ok().body(getJobInfo(jobDetail, trigger));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{jobName}")
    public ResponseEntity<Object> deleteJob(@PathVariable String jobName) {
        try {
            boolean result = scheduler.unscheduleJob(TriggerKey.triggerKey("trigger_" + jobName));
            Map<String, Object> response = new HashMap<>();
            response.put("status", result);
            return ResponseEntity.ok().body(response);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<Object> listJob() {
        List<JobInfo> result = new ArrayList<>();
        try {
            for (String group : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group))) {
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey("trigger_" + jobKey.getName()));
                    result.add(getJobInfo(jobDetail, trigger));
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(result);
    }

    private JobInfo getJobInfo(JobDetail jobDetail, Trigger trigger) {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setDescription(jobDetail.getDescription());
        jobInfo.setName(jobDetail.getKey().getName());
        jobInfo.setLastRun(trigger.getPreviousFireTime());
        jobInfo.setNextRun(trigger.getNextFireTime());
        return jobInfo;
    }
}
