package com.example.quartz.qschedule.job;

import com.example.quartz.domain.dao.SettingDaoHibernateImpl;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloJob implements Job {


    private SettingDaoHibernateImpl settingDaoHibernateImpl;

    public HelloJob() {
    }

    @Autowired
    public void setSettingDaoHibernateImpl(SettingDaoHibernateImpl settingDaoHibernateImpl) {
        this.settingDaoHibernateImpl = settingDaoHibernateImpl;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        JobDataMap data = context.getMergedJobDataMap();
        System.err.println("Hello!  HelloJob is executing with key :"+context.getJobDetail().getKey()+" and id "+data.get("job_id").toString());
        settingDaoHibernateImpl.updateRow(data.get("job_id").toString());
    }

}
