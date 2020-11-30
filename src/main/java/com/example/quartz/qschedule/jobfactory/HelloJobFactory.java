package com.example.quartz.qschedule.jobfactory;

import com.example.quartz.config.ZeroSetting;
import com.example.quartz.domain.dao.SettingDaoHibernateImpl;
import com.example.quartz.qschedule.job.HelloJob;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.stereotype.Component;

@Component
public class HelloJobFactory implements JobFactory {
    private SettingDaoHibernateImpl settingDaoHibernateImpl;
    private ZeroSetting s;

    public HelloJobFactory(SettingDaoHibernateImpl settingDaoHibernateImpl,ZeroSetting s) {
        this.settingDaoHibernateImpl = settingDaoHibernateImpl;
        this.s=s;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) {
        return new HelloJob(settingDaoHibernateImpl, s);
    }
}
