package com.example.quartz.scheduler;


import com.example.quartz.config.ZeroSetting;
import com.example.quartz.domain.dao.SettingDaoHibernateImpl;
import com.example.quartz.domain.model.Settings;
import com.example.quartz.qschedule.job.ByeJob;
import com.example.quartz.qschedule.job.HelloJob;
import com.example.quartz.qschedule.jobfactory.ByeJobFactory;
import com.example.quartz.qschedule.jobfactory.HelloJobFactory;
import com.example.quartz.qschedule.joblistener.HelloJobListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;
import static org.quartz.impl.matchers.GroupMatcher.groupEquals;

@Component
public class TestScheduler {

    Logger logger = LoggerFactory.getLogger(getClass());


    private Scheduler instanceH;
    private Scheduler instanceB;
    private SettingDaoHibernateImpl settingDaoHibernateImpl;
    private ZeroSetting settings;
    /*public TestScheduler() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }*/
    @Autowired
    public TestScheduler(HelloJobFactory jobFactory, ByeJobFactory jonFactoryB,
                         @Qualifier("hello") StdSchedulerFactory factoryH, @Qualifier("bye") StdSchedulerFactory factoryB,
                         SettingDaoHibernateImpl settingDaoHibernateImpl, ZeroSetting settings) {
         try{
             this.settingDaoHibernateImpl = settingDaoHibernateImpl;
             this.settings  = settings;

             instanceH = factoryH.getScheduler();
             instanceH.setJobFactory(jobFactory);
             instanceH.start();

             instanceB = factoryB.getScheduler();
             instanceB.setJobFactory(jonFactoryB);
             instanceB.start();

             logger.debug("Quartz matching job scheduler started running");
         } catch (SchedulerException ex) {
             logger.error("There was a problem with scheduler or job factory", ex);
             throw new RuntimeException(ex);
         }

    }

    @Scheduled(fixedDelay = 5000)
    public void scheduleFixedDelayTask() {
        try {

        //add jobs and triggers into sched (old job and triggers replaced by new)
        for (Settings row :settingDaoHibernateImpl.getAllSettings()){
            Date start = new Date(row.getStart().getTime());
            Date end = new Date(row.getEnd().getTime());

            JobDetail startJob = driverMatcherJobDetailHello(row.getId().toString());
            JobDetail endJob = driverMatcherJobDetailBye(row.getId().toString());

            Trigger triggerHello = simpleTriggerWithIdentityForHello(startJob,start,row.getId().toString());
            Trigger triggerBye = simpleTriggerWithIdentityForBye(endJob,end,row.getId().toString());

            logger.info("bean : "+settings.getName());

            if (!instanceH.getJobKeys(groupEquals("group1")).contains(startJob.getKey()) ) {
                instanceH.scheduleJob(startJob, triggerHello);
            }
            if (! instanceB.getJobKeys(groupEquals("group2")).contains(endJob.getKey())) {
                instanceB.scheduleJob(endJob, triggerBye);
            }
            logger.info("bean after : "+settings.getName());
        }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("group1 : "+instanceH.getJobKeys(groupEquals("group1")));
            System.out.println("group2 : "+instanceB.getJobKeys(groupEquals("group2")));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() {
        logger.info("Hello world from Quartz...");
    }

    private Trigger simpleTriggerWithIdentityForHello(JobDetail jobDetail, Date localDateTime, String triggerKey) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(TriggerKey.triggerKey("starttrigger"+triggerKey))
                .startAt(localDateTime)
                .build();
    }

    private Trigger simpleTriggerWithIdentityForBye(JobDetail jobDetail, Date localDateTime, String triggerKey) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(TriggerKey.triggerKey("endtrigger"+triggerKey))
                .startAt(localDateTime)
                .build();
    }
    private JobDetail driverMatcherJobDetailHello(String rideId) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("job_id", rideId);

        return JobBuilder.newJob().ofType(HelloJob.class).withIdentity("startjob_"+rideId, "group1").
                usingJobData(jobDataMap).storeDurably(true).build();
    }

    private JobDetail driverMatcherJobDetailBye(String rideId) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("job_id", rideId);
        return JobBuilder.newJob().ofType(ByeJob.class).withIdentity("endjob_"+rideId, "group2").
                usingJobData(jobDataMap).storeDurably(true).build();
    }
}
