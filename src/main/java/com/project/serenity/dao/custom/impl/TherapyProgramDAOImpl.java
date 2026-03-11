package com.project.serenity.dao.custom.impl;

import com.project.serenity.dao.custom.TherapyProgramDAO;
import com.project.serenity.entity.TherapyProgram;
import com.project.serenity.utill.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class TherapyProgramDAOImpl implements TherapyProgramDAO {

    @Override
    public boolean save(TherapyProgram program) throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(program);
            transaction.commit();
            return true;
        } catch (Exception e) {
            throw new SQLException("Failed to save Therapy Program", e);
        }
    }

    @Override
    public boolean update(TherapyProgram program) throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(program);
            transaction.commit();
            return true;
        } catch (Exception e) {
            throw new SQLException("Failed to update Therapy Program", e);
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            TherapyProgram program = session.get(TherapyProgram.class, id);
            if (program != null) {
                session.remove(program);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new SQLException("Failed to delete Therapy Program", e);
        }
    }

    @Override
    public TherapyProgram get(int id) throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession()) {
            return session.get(TherapyProgram.class, id);
        } catch (Exception e) {
            throw new SQLException("Failed to retrieve Therapy Program", e);
        }
    }

    @Override
    public List<TherapyProgram> getAll() throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession()) {
            Query<TherapyProgram> query = session.createQuery("from TherapyProgram", TherapyProgram.class);
            return query.list();
        } catch (Exception e) {
            throw new SQLException("Failed to retrieve all Therapy Programs", e);
        }
    }
}
