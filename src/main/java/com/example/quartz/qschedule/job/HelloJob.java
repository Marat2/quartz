package com.example.quartz.qschedule.job;

import com.example.quartz.config.ZeroSetting;
import com.example.quartz.domain.dao.SettingDaoHibernateImpl;
import com.example.quartz.domain.model.Settings;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.query.Query;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;


public class HelloJob implements Job {


    //private SettingDaoHibernateImpl settingDaoHibernateImpl;

    private ZeroSetting z ;
    public HelloJob() {
    }

    public HelloJob(/*SettingDaoHibernateImpl settingDaoHibernateImpl,*/ ZeroSetting z) {
        //this.settingDaoHibernateImpl = settingDaoHibernateImpl;
        this.z=z;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        JobDataMap data = context.getMergedJobDataMap();
        System.err.println("Hello!  HelloJob is executing with id "+data.get("job_id").toString());

        System.out.println(data.get("job_id").toString().startsWith("end_"));

        if (data.get("job_id").toString().startsWith("end_")){
            System.out.println("ggg");
            //String id = data.get("job_id").toString().substring(data.get("job_id").toString().indexOf("_")+1);
            Long id = Long.valueOf(data.get("job_id").toString().substring(data.get("job_id").toString().indexOf("_")+1));
            Sf s = new Sf();
            Session session =  s.getHibernateConnect().openSession();
            Transaction txn = session.beginTransaction();
            String hql = "update Settings s set s.status = 'OLD' where s.id=:id";
            Query query3 = session.createQuery(hql);
            query3.setParameter("id", id);

            query3.executeUpdate();
            txn.commit();
            session.close();

            z.setName("With comission");
        }else{
            System.out.println("zzzzz");
            z.setName("Without comission");
        }
    }


    public class Sf{
        public  SessionFactory sessionFactory;
        public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
        public static final String USER = "mobile";
        public static final String PASS = "NVxzpq1tmoi";
        public static final String URL = "jdbc:oracle:thin:@172.25.1.11:1521:prepaid";

        public  SessionFactory getHibernateConnect(){
            Map<String,String> settings = new HashMap<>();
            settings.put(Environment.DRIVER, DRIVER);
            settings.put(Environment.USER, USER);
            settings.put(Environment.PASS, PASS);
            settings.put(Environment.URL, URL);
            if (sessionFactory==null) {
                StandardServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(settings).build();
                MetadataSources metadataSources = new MetadataSources(registry);
                metadataSources.addAnnotatedClass(Settings.class);
                Metadata metadata = metadataSources.buildMetadata();
                sessionFactory = metadata.buildSessionFactory();
            }
            return sessionFactory;
        }
    }


}
