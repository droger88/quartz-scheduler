package com.demo.scheduler.job;

import com.demo.scheduler.service.Service;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class MyJob implements Job {

    @Autowired
    private Service service;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobData = context.getMergedJobDataMap();
        service.doSomething(jobData.getWrappedMap());
    }

}
