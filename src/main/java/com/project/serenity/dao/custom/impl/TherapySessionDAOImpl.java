package com.project.serenity.dao.custom.impl;

import com.project.serenity.dao.custom.TherapySessionDAO;
import com.project.serenity.entity.TherapySession;
import com.project.serenity.utill.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class TherapySessionDAOImpl implements TherapySessionDAO {

    @Override
    public boolean save(TherapySession session) {
        Session hibernateSession = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        Transaction transaction = hibernateSession.beginTransaction();

        try {
            hibernateSession.save(session);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            hibernateSession.close();
        }
    }

    @Override
    public boolean update(TherapySession session) {
        Session hibernateSession = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        Transaction transaction = hibernateSession.beginTransaction();

        try {
            hibernateSession.update(session);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            hibernateSession.close();
        }
    }

    @Override
    public boolean delete(int id) {
        Session hibernateSession = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        Transaction transaction = hibernateSession.beginTransaction();

        try {
            TherapySession session = hibernateSession.get(TherapySession.class, id);
            if (session != null) {
                hibernateSession.delete(session);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            hibernateSession.close();
        }
    }

    @Override
    public TherapySession get(int id) {
        Session hibernateSession = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        try {
            return hibernateSession.get(TherapySession.class, id);
        } finally {
            hibernateSession.close();
        }
    }

    @Override
    public List<TherapySession> getAll() {
        Session hibernateSession = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        try {
            Query<TherapySession> query = hibernateSession.createQuery("from TherapySession", TherapySession.class);
            return query.list();
        } finally {
            hibernateSession.close();
        }
    }

    @Override
    public List<TherapySession> searchByPatientId(int patientId) {
        Session hibernateSession = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        try {
            Query<TherapySession> query = hibernateSession.createQuery(
                    "from TherapySession where patient.patientId = :patientId", TherapySession.class);
            query.setParameter("patientId", patientId);
            return query.list();
        } finally {
            hibernateSession.close();
        }
    }

    @Override
    public List<TherapySession> searchByTherapistId(int therapistId) {
        Session hibernateSession = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        try {
            Query<TherapySession> query = hibernateSession.createQuery(
                    "from TherapySession where therapist.therapistId = :therapistId", TherapySession.class);
            query.setParameter("therapistId", therapistId);
            return query.list();
        } finally {
            hibernateSession.close();
        }
    }
}
