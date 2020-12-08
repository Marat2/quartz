package com.example.quartz.config;

import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = "com.example.quartz")
public class SpringConfig {

    @Bean(name = "hello")
    public StdSchedulerFactory matchingSchedulerFactoryH() {
        return new StdSchedulerFactory();
    }

    /*@Bean(name = "bye")
    public StdSchedulerFactory matchingSchedulerFactoryB() {
        return new StdSchedulerFactory();
    }*/
    @Bean
    public ZeroSetting setZero(){
        return new ZeroSetting();
    }


}
