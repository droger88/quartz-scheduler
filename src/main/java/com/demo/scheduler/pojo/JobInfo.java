package com.demo.scheduler.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class JobInfo {
    private Date nextRun;
    private String name;
    private String description;
    private Date lastRun;
}
