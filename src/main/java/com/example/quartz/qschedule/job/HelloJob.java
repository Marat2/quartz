package com.example.quartz.qschedule.job;

import com.example.quartz.config.ZeroSetting;
import com.example.quartz.domain.dao.SettingDaoHibernateImpl;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class HelloJob implements Job {


    private SettingDaoHibernateImpl settingDaoHibernateImpl;

    private ZeroSetting z ;
    public HelloJob() {
    }

    public HelloJob(SettingDaoHibernateImpl settingDaoHibernateImpl, ZeroSetting z) {
        this.settingDaoHibernateImpl = settingDaoHibernateImpl;
        this.z=z;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        JobDataMap data = context.getMergedJobDataMap();
        System.err.println("Hello!  HelloJob is executing with id "+data.get("job_id").toString());

        System.out.println(data.get("job_id").toString().startsWith("end_"));

        if (data.get("job_id").toString().startsWith("end_")){
            System.out.println("ggg");
            String id = data.get("job_id").toString().substring(data.get("job_id").toString().indexOf("_")+1);
            settingDaoHibernateImpl.updateRow(id);
            z.setName("With comission");
        }else{
            System.out.println("zzzzz");
            z.setName("Without comission");
        }
    }

}
