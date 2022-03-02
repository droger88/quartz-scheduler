package com.demo.scheduler.pojo;
import java.util.Map;

import lombok.Data;

@Data
public class ScheduleRequest{

    private String cron;
    private String jobDescription;
    private JobType jobType;
    private Map<String,Object> data;
}