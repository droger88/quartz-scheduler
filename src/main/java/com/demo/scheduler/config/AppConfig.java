package com.demo.scheduler.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.Scheduler;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class AppConfig {

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) {
        return schedulerFactoryBean.getScheduler();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(SpringBeanJobFactory springBeanJobFactory) throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(springBeanJobFactory);
        schedulerFactoryBean.setQuartzProperties(properties());
        return schedulerFactoryBean;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory autowiringSpringBeanJobFactory = new AutowiringSpringBeanJobFactory();
        autowiringSpringBeanJobFactory.setApplicationContext(applicationContext);
        return autowiringSpringBeanJobFactory;
    }


    private Properties properties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
