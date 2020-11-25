package com.example.quartz.domain.util;

import com.example.quartz.domain.model.Settings;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.util.HashMap;
import java.util.Map;

public class Util {

    public static SessionFactory sessionFactory;
    public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static final String USER = "mobile";
    public static final String PASS = "NVxzpq1tmoi";
    public static final String URL = "jdbc:oracle:thin:@172.25.1.11:1521:prepaid";


    public static SessionFactory getHibernateConnect(){
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
