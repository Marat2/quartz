package com.example.quartz.scheduler;


import com.example.quartz.domain.dao.SettingDaoHibernateImpl;
import com.example.quartz.qschedule.job.HelloJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Component
public class TestScheduler {

    @Autowired
    SettingDaoHibernateImpl settingDaoHibernateImpl;
    @Scheduled(fixedDelay = 100000)
    public void scheduleFixedDelayTask() {

        if (settingDaoHibernateImpl.getAllSettings().get(0).getStatus().equals("new")){
            Date date=new Date(settingDaoHibernateImpl.getAllSettings().get(0).getStart().getTime());
            System.out.println("Fixed delay task - " + date);
            try {
                Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                scheduler.start();
                JobDetail job = newJob(HelloJob.class).withIdentity("job1", "group1").build();

                SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                        .withIdentity("trigger1", "group1")
                        .startAt(date) // some Date
                        .forJob("job1", "group1") // identify job with name, group strings
                        .build();
                scheduler.scheduleJob(job, trigger);

                scheduler.shutdown();
            } catch (SchedulerException se) {
                se.printStackTrace();
            }
        }

    }
}
