package com.example.quartz.qschedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ByeJob implements Job {

    public ByeJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        System.err.println("Bye!  ByeJob is executing.");
    }
}
