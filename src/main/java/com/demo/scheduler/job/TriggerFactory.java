package com.demo.scheduler.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import lombok.Data;

@Data
public class TriggerFactory {
    private String cron;
    private JobDetail jobDetail;

    public TriggerFactory setCron(String cron) {
        this.cron = cron;
        return this;
    }

    public TriggerFactory setJobDetail(JobDetail jobDetail) {
        this.jobDetail = jobDetail;
        return this;
    }

    public Trigger build() {
        return TriggerBuilder.newTrigger()
                .withDescription("trigger for " + this.jobDetail.getDescription())
                .withIdentity("trigger_" + this.jobDetail.getKey().getName())
                .withSchedule(CronScheduleBuilder.cronSchedule(this.cron))
                .build();
    }
}
