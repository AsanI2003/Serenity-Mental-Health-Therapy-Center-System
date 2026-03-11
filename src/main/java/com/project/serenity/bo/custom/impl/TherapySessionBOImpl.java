package com.project.serenity.bo.custom.impl;

import com.project.serenity.bo.custom.TherapySessionBO;
import com.project.serenity.dao.DAOFactory;
import com.project.serenity.dao.custom.PaymentDAO;
import com.project.serenity.dao.custom.PatientDAO;
import com.project.serenity.dao.custom.TherapistDAO;
import com.project.serenity.dao.custom.TherapyProgramDAO;
import com.project.serenity.dao.custom.TherapySessionDAO;
import com.project.serenity.dto.TherapySessionDto;
import com.project.serenity.entity.Payment;
import com.project.serenity.entity.Patient;
import com.project.serenity.entity.Therapist;
import com.project.serenity.entity.TherapyProgram;
import com.project.serenity.entity.TherapySession;
import com.project.serenity.utill.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TherapySessionBOImpl implements TherapySessionBO {

    TherapySessionDAO therapySessionDAO = (TherapySessionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPYSESSION);
    TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPIST);
    PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PATIENT);
    TherapyProgramDAO therapyProgramDAO = (TherapyProgramDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPYPROGRAM);
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public boolean saveTherapySession(TherapySessionDto dto) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Therapist therapist = session.get(Therapist.class, dto.getTherapistId());
            Patient patient = session.get(Patient.class, dto.getPatientId());
            TherapyProgram program = session.get(TherapyProgram.class, dto.getProgramId());

            if (therapist == null || patient == null || program == null) {
                throw new RuntimeException("One or more related IDs not found!");
            }

            Payment payment = new Payment();
            payment.setPaidAmount(0.0);
            payment.setPendingAmount(0.0);
            payment.setPaymentStatus("check this");

            session.persist(payment);

            TherapySession therapySession = new TherapySession();
            therapySession.setBookedat(dto.getBookedat());
            therapySession.setDuration(dto.getDuration());
            therapySession.setTherapist(therapist);
            therapySession.setPatient(patient);
            therapySession.setProgram(program);
            therapySession.setPayment(payment);

            session.persist(therapySession);

            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean updateTherapySession(TherapySessionDto dto) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            TherapySession sessionEntity = session.get(TherapySession.class, dto.getSessionId());
            if (sessionEntity == null) {
                throw new RuntimeException("Session not found!");
            }

            Therapist therapist = session.get(Therapist.class, dto.getTherapistId());
            Patient patient = session.get(Patient.class, dto.getPatientId());
            TherapyProgram program = session.get(TherapyProgram.class, dto.getProgramId());

            sessionEntity.setBookedat(dto.getBookedat());
            sessionEntity.setDuration(dto.getDuration());
            sessionEntity.setTherapist(therapist);
            sessionEntity.setPatient(patient);
            sessionEntity.setProgram(program);

            session.merge(sessionEntity);

            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean deleteTherapySession(int sessionId) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            TherapySession therapySession = session.get(TherapySession.class, sessionId);
            if (therapySession != null) {
                session.remove(therapySession);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public TherapySessionDto getTherapySession(int sessionId) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        try {
            TherapySession therapySession = session.get(TherapySession.class, sessionId);
            if (therapySession != null) {
                return new TherapySessionDto(
                        therapySession.getSessionId(),
                        therapySession.getBookedat(),
                        therapySession.getDuration(),
                        therapySession.getTherapist().getTherapistId(),
                        therapySession.getPatient().getPatientId(),
                        therapySession.getProgram().getProgramId(),
                        therapySession.getPayment().getPaymentId()
                );
            }
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<TherapySessionDto> getAllTherapySessions() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        try {
            List<TherapySession> therapySessions = session.createQuery("FROM TherapySession", TherapySession.class).list();
            List<TherapySessionDto> dtos = new ArrayList<>();
            for (TherapySession sessionEntity : therapySessions) {
                dtos.add(new TherapySessionDto(
                        sessionEntity.getSessionId(),
                        sessionEntity.getBookedat(),
                        sessionEntity.getDuration(),
                        sessionEntity.getTherapist().getTherapistId(),
                        sessionEntity.getPatient().getPatientId(),
                        sessionEntity.getProgram().getProgramId(),
                        sessionEntity.getPayment().getPaymentId()
                ));
            }
            return dtos;
        } finally {
            session.close();
        }
    }

    @Override
    public List<TherapySessionDto> searchTherapySessionsByPatientId(int patientId) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        try {
            List<TherapySession> list = session.createQuery(
                            "FROM TherapySession WHERE patient.patientId = :id", TherapySession.class)
                    .setParameter("id", patientId)
                    .list();
            List<TherapySessionDto> dtos = new ArrayList<>();
            for (TherapySession sessionEntity : list) {
                dtos.add(new TherapySessionDto(
                        sessionEntity.getSessionId(),
                        sessionEntity.getBookedat(),
                        sessionEntity.getDuration(),
                        sessionEntity.getTherapist().getTherapistId(),
                        sessionEntity.getPatient().getPatientId(),
                        sessionEntity.getProgram().getProgramId(),
                        sessionEntity.getPayment().getPaymentId()
                ));
            }
            return dtos;
        } finally {
            session.close();
        }
    }

    @Override
    public List<TherapySessionDto> searchTherapySessionsByTherapistId(int therapistId) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession();
        try {
            List<TherapySession> list = session.createQuery(
                            "FROM TherapySession WHERE therapist.therapistId = :id", TherapySession.class)
                    .setParameter("id", therapistId)
                    .list();
            List<TherapySessionDto> dtos = new ArrayList<>();
            for (TherapySession sessionEntity : list) {
                dtos.add(new TherapySessionDto(
                        sessionEntity.getSessionId(),
                        sessionEntity.getBookedat(),
                        sessionEntity.getDuration(),
                        sessionEntity.getTherapist().getTherapistId(),
                        sessionEntity.getPatient().getPatientId(),
                        sessionEntity.getProgram().getProgramId(),
                        sessionEntity.getPayment().getPaymentId()
                ));
            }
            return dtos;
        } finally {
            session.close();
        }
    }
}
