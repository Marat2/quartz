package com.example.quartz.qschedule.jobfactory;

import com.example.quartz.config.ZeroSetting;
import com.example.quartz.domain.dao.SettingDaoHibernateImpl;
import com.example.quartz.qschedule.job.HelloJob;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class HelloJobFactory implements JobFactory , ApplicationContextAware {
    //private SettingDaoHibernateImpl settingDaoHibernateImpl;


    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /*public HelloJobFactory(SettingDaoHibernateImpl settingDaoHibernateImpl) {
        this.settingDaoHibernateImpl = settingDaoHibernateImpl;
    }*/

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) {
        ZeroSetting a = applicationContext.getBean( ZeroSetting.class );

        return new HelloJob(/*settingDaoHibernateImpl,*/ a);
    }
}
