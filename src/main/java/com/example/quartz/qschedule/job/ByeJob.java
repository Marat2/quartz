package com.example.quartz.qschedule.job;

import com.example.quartz.config.ZeroSetting;
import com.example.quartz.domain.dao.SettingDaoHibernateImpl;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class ByeJob implements Job {


    private ZeroSetting z ;

    public ByeJob() {
    }

    public ByeJob(ZeroSetting z) {
        this.z = z;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        System.err.println("Bye!  ByeJob is executing.");
        //JobDataMap data = context.getMergedJobDataMap();
        z.setName("ssdssd");
    }
}
