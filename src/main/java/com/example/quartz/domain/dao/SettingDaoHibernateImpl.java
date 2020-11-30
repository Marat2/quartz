package com.example.quartz.domain.dao;

import com.example.quartz.domain.model.Settings;
import com.example.quartz.domain.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Component
public class SettingDaoHibernateImpl implements SettingDao{
    @Override
    public List<Settings> getAllSettings() {
        Session session = Util.getHibernateConnect().openSession();
        session.beginTransaction();
        String hql = "FROM Settings WHERE status = 'new'";
        Query query = session.createQuery(hql);
        List results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;
    }

    @Override
    public void updateRow(String id) {
        Session session = Util.getHibernateConnect().openSession();
        Transaction txn = session.beginTransaction();
        String hql = "update Settings s set s.status = 'old' where s.id=:id";
        Query query3 = session.createQuery(hql);
        query3.setParameter("id", Long.valueOf(id));

        query3.executeUpdate();
        txn.commit();
        session.close();
    }


}
