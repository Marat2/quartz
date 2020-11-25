package com.example.quartz.qschedule.joblistener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class HelloJobListener implements JobListener {

    private String name;

    public HelloJobListener(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void jobToBeExecuted(JobExecutionContext context) {
        // do something with the event
    }

    public void jobWasExecuted(JobExecutionContext context,JobExecutionException jobException) {
        System.out.println("jon_key"+context.getJobDetail().getKey());
        System.out.println(context.getMergedJobDataMap().getString("start_date"));
        System.out.println(context.getMergedJobDataMap().getString("end_date"));
        System.out.println("job was executed update db");
    }

    public void jobExecutionVetoed(JobExecutionContext context) {
        // do something with the event
    }
}
