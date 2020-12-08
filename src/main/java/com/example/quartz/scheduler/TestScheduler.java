package com.example.quartz.scheduler;


import com.example.quartz.config.ZeroSetting;
import com.example.quartz.domain.dao.SettingDaoHibernateImpl;
import com.example.quartz.domain.model.Settings;
import com.example.quartz.qschedule.job.ByeJob;
import com.example.quartz.qschedule.job.HelloJob;
import com.example.quartz.qschedule.jobfactory.HelloJobFactory;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;


import static org.quartz.impl.matchers.GroupMatcher.groupEquals;

@Component
public class TestScheduler {

    Logger logger = LoggerFactory.getLogger(getClass());

    private Scheduler instanceH;
    private SettingDaoHibernateImpl settingDaoHibernateImpl;
    private ZeroSetting settings;

    //@Autowired
    public TestScheduler(HelloJobFactory jobFactory,
                         @Qualifier("hello") StdSchedulerFactory factoryH,
                         SettingDaoHibernateImpl settingDaoHibernateImpl, ZeroSetting settings) {
         try{
             this.settingDaoHibernateImpl = settingDaoHibernateImpl;
             this.settings  = settings;

             instanceH = factoryH.getScheduler();
             instanceH.setJobFactory(jobFactory);
             instanceH.start();
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
        /*for (Settings row :settingDaoHibernateImpl.getAllSettings()){

            Date start =  Date.from(row.getStart().toInstant());
            Date end =  Date.from(row.getEnd().toInstant());

            JobDetail startJob = driverMatcherJobDetailHello(row.getId().toString());
            JobDetail endJob = driverMatcherJobDetailHello("end_"+row.getId().toString());
            Trigger triggerHello = simpleTriggerWithIdentityForHello(startJob,start,row.getId().toString());
            Trigger triggerBye = simpleTriggerWithIdentityForHello(endJob,end,"end_"+row.getId().toString());
            logger.info("bean : "+settings.getName());

            if (!instanceH.getJobKeys(groupEquals("group1")).contains(startJob.getKey())) {
                instanceH.scheduleJob(startJob, triggerHello);
            }
            if (!instanceH.getJobKeys(groupEquals("group1")).contains(endJob.getKey()) ) {
                instanceH.scheduleJob(endJob, triggerBye);
            }
            logger.info("bean after : "+settings.getName());
        }*/
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private Trigger simpleTriggerWithIdentityForHello(JobDetail jobDetail, Date localDateTime, String triggerKey) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(TriggerKey.triggerKey("starttrigger"+triggerKey))
                .startAt(localDateTime)
                .build();
    }

    private JobDetail driverMatcherJobDetailHello(String rideId) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("job_id", rideId);

        return JobBuilder.newJob().ofType(HelloJob.class).withIdentity("startjob_"+rideId, "group1").
                usingJobData(jobDataMap).storeDurably(true).build();
    }
}
