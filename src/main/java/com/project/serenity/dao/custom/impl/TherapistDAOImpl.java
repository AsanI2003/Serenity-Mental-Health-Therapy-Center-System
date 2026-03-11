package com.project.serenity.dao.custom.impl;

import com.project.serenity.dao.CrudDAO;
import com.project.serenity.dao.custom.TherapistDAO;
import com.project.serenity.entity.Therapist;
import com.project.serenity.utill.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class TherapistDAOImpl implements TherapistDAO {

    @Override
    public boolean save(Therapist therapist) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(therapist);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            throw new SQLException("Failed to save Therapist", e);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Therapist therapist) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(therapist);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            throw new SQLException("Failed to update Therapist", e);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Therapist therapist = session.get(Therapist.class, id);
            if (therapist != null) {
                session.delete(therapist);
                tx.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            tx.rollback();
            throw new SQLException("Failed to delete Therapist", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Therapist get(int id) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        try {
            return session.get(Therapist.class, id);
        } catch (Exception e) {
            throw new SQLException("Failed to retrieve Therapist", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Therapist> getAll() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Therapist", Therapist.class).list();
        } catch (Exception e) {
            throw new SQLException("Failed to retrieve all Therapists", e);
        } finally {
            session.close();
        }
    }
}
