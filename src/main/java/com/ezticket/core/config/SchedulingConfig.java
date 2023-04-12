package com.ezticket.core.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("com.ezticket.web.*.service")
@EnableScheduling
public class SchedulingConfig {


}