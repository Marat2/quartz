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
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String USER = "jmuser";
    public static final String PASS = "QazQwe!23456";
    public static final String URL = "jdbc:mysql://localhost:3306/jm?useSSL=false&serverTimezone=UTC";


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
