package com.project.serenity.dao.custom;

import com.project.serenity.dao.CrudDAO;
import com.project.serenity.entity.TherapySession;

import java.util.List;

public interface TherapySessionDAO extends CrudDAO<TherapySession> {

    List<TherapySession> searchByPatientId(int patientId);

    List<TherapySession> searchByTherapistId(int therapistId);

}
