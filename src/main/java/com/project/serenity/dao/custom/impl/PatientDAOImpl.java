package com.project.serenity.dao.custom.impl;

import com.project.serenity.dao.custom.PatientDAO;
import com.project.serenity.entity.Patient;
import com.project.serenity.utill.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PatientDAOImpl implements PatientDAO {
    public boolean save(Patient patient) {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(patient);
        tx.commit();
        session.close();
        return true;
    }

    public boolean update(Patient patient) {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(patient);
        tx.commit();
        session.close();
        return true;
    }

    public boolean delete(int id) {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Patient patient = session.get(Patient.class, id);
        if (patient != null) session.delete(patient);
        tx.commit();
        session.close();
        return true;
    }

    public Patient get(int id) {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        Patient patient = session.get(Patient.class, id);
        session.close();
        return patient;
    }

    public List<Patient> getAll() {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        Query<Patient> query = session.createQuery("FROM Patient", Patient.class);
        List<Patient> list = query.list();
        session.close();
        return list;
    }
}