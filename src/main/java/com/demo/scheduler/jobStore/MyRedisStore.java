package com.demo.scheduler.jobStore;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.quartz.spi.SchedulerSignaler;

import net.joelinn.quartz.jobstore.RedisJobStore;
import net.joelinn.quartz.jobstore.RedisJobStoreSchema;
import net.joelinn.quartz.jobstore.RedisStorage;

public class MyRedisStore extends RedisJobStore{

    @Override
    public long getAcquireRetryDelay(int failureCount){
        return 0;
    }
    
}
