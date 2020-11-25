package com.example.quartz.scheduler;


import com.example.quartz.domain.dao.SettingDaoHibernateImpl;
import com.example.quartz.domain.model.Settings;
import com.example.quartz.qschedule.job.ByeJob;
import com.example.quartz.qschedule.job.HelloJob;
import com.example.quartz.qschedule.joblistener.HelloJobListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import static org.quartz.JobKey.*;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

@Component
public class TestScheduler {

    Scheduler scheduler;


    public TestScheduler() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @Autowired
    SettingDaoHibernateImpl settingDaoHibernateImpl;
    @Scheduled(fixedDelay = 20000)
    public void scheduleFixedDelayTask() {
        try {

        //add jobs and triggers into sched (old job and triggers replaced by new)
        for (Settings row :settingDaoHibernateImpl.getAllSettings()){
            Date start = new Date(settingDaoHibernateImpl.getAllSettings().get(0).getStart().getTime());
            Date end = new Date(settingDaoHibernateImpl.getAllSettings().get(0).getEnd().getTime());

            System.out.println(row.toString());


            JobDetail StartJob = newJob(HelloJob.class).withIdentity("startjob_"+row.getId(), "group1").
                    usingJobData("start_date",start.getTime()).usingJobData("end_date",end.getTime()).storeDurably(true).build();

            SimpleTrigger StartTrigger = (SimpleTrigger) newTrigger().withIdentity("starttrigger"+row.getId(), "group1")
                    .startAt(start).forJob("startjob_"+row.getId(), "group1").build();
            HelloJobListener jb = new HelloJobListener("startjob_"+row.getId()+"_listener");

            JobDetail EndJob = newJob(ByeJob.class).withIdentity("endjob_"+row.getId(), "group1").storeDurably(true).build();

            SimpleTrigger EndTrigger = (SimpleTrigger) newTrigger().withIdentity("endtrigger1"+row.getId(), "group1")
                    .startAt(end).forJob("endjob_"+row.getId(), "group1").build();

            scheduler.addJob(StartJob, true);
            scheduler.addJob(EndJob, true);

                //add listener to job
                scheduler.getListenerManager().addJobListener(jb, KeyMatcher.keyEquals(jobKey("startjob_"+row.getId(), "group1")));
                //add triggers to sched
                scheduler.rescheduleJob(triggerKey("starttrigger"+row.getId(), "group1"), StartTrigger);
                scheduler.rescheduleJob(triggerKey("endtrigger1"+row.getId(), "group1"), EndTrigger);

        }

        //обойти триггеры и установить job

        /*for(TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.groupEquals("group1"))) {
            Trigger tr = scheduler.getTrigger(triggerKey);
            System.out.println("Found trigger identified by: " + triggerKey);

            //try tell trigger to schedule the job
            //scheduler.scheduleJob(StartJob,StartTrigger);
            //scheduler.scheduleJob(EndJob,EndTrigger);
        }*/

        } catch (SchedulerException e) {
            e.printStackTrace();
        }


        /*if (settingDaoHibernateImpl.getAllSettings().get(0).getStatus().equals("new")){
            Date date=new Date(settingDaoHibernateImpl.getAllSettings().get(0).getStart().getTime());
            System.out.println("Fixed delay task - " + date);
            try {
                //Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                //scheduler.start();

                JobDetail job = newJob(HelloJob.class).withIdentity("job1", "group1").build();
                SimpleTrigger trigger = (SimpleTrigger) newTrigger().withIdentity("trigger1", "group1")
                        .startAt(date).forJob("job1", "group1").build();
                scheduler.scheduleJob(job, trigger);

                scheduler.shutdown();
            } catch (SchedulerException se) {
                se.printStackTrace();
            }
        }*/

    }
}
