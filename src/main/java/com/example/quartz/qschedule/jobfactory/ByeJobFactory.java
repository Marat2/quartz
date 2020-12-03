package com.example.quartz.qschedule.jobfactory;

import com.example.quartz.config.ZeroSetting;
import com.example.quartz.qschedule.job.ByeJob;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.stereotype.Component;

@Component
public class ByeJobFactory implements JobFactory {

    private ZeroSetting s;

    public ByeJobFactory(ZeroSetting s) {
        this.s = s;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) {
        return new ByeJob(s);
    }
}
