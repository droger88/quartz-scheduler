package com.demo.scheduler.job;

import org.quartz.Job;
import java.util.HashMap;
import java.util.Map;

import com.demo.scheduler.pojo.JobType;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

import lombok.Data;

@Data
public class JobFactory {

    private String description;
    private JobType jobType;
    private String jobName;
    private Map<String, Object> jobData;
    private static Map<JobType, Class<? extends Job>> jobTypeMapping = new HashMap<>();

    static {
        jobTypeMapping.put(JobType.TYPE_A, MyJob.class);
    }

    public JobFactory setDescription(String description) {
        this.description = description;
        return this;
    }

    public JobFactory setJobType(JobType jobType) {
        this.jobType = jobType;
        return this;
    }

    public JobFactory setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public JobFactory setJobData(Map<String, Object> jobData) {
        this.jobData = jobData;
        return this;
    }

    /**
     * create job details
     * 
     * @return
     */
    public JobDetail build() {
        Class<? extends Job> job = jobTypeMapping.get(this.getJobType());
        JobDataMap jobDataMap = new JobDataMap();
        if(this.jobData != null){
            jobDataMap.putAll(this.jobData);
        }
        return JobBuilder.newJob()
                .ofType(job)
                .withDescription(this.description)
                .withIdentity(jobName)
                .setJobData(jobDataMap)
                .build();
    }
}
