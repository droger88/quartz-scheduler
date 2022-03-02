package com.demo.scheduler.service;

import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Service {

    public void doSomething(Map<String, Object> jobData) {
        log.info("do something here with job data {}", jobData);
    }
}
