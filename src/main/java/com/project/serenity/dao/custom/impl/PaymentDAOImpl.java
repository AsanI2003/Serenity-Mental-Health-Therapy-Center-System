package com.project.serenity.dao.custom.impl;

import com.project.serenity.dao.custom.PaymentDAO;
import com.project.serenity.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.project.serenity.utill.FactoryConfiguration;

import java.sql.SQLException;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public boolean save(Payment payment) throws SQLException {
        throw new UnsupportedOperationException("Save not implemented for Payment.");
    }

    @Override
    public boolean update(Payment payment) throws SQLException {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(payment);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        throw new UnsupportedOperationException("Delete not implemented for Payment.");
    }

    @Override
    public Payment get(int id) throws SQLException {
        throw new UnsupportedOperationException("Get (by id) not implemented for Payment.");
    }

    @Override
    public List<Payment> getAll() throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession()) {
            return session.createQuery("FROM Payment", Payment.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
