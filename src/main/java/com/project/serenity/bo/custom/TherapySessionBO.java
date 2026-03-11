package com.project.serenity.bo.custom;

import com.project.serenity.bo.SuperBO;
import com.project.serenity.dto.TherapySessionDto;

import java.util.List;

public interface TherapySessionBO extends SuperBO {

    boolean saveTherapySession(TherapySessionDto dto) throws Exception;

    boolean updateTherapySession(TherapySessionDto dto) throws Exception;

    boolean deleteTherapySession(int sessionId) throws Exception;

    TherapySessionDto getTherapySession(int sessionId) throws Exception;

    List<TherapySessionDto> getAllTherapySessions() throws Exception;

    List<TherapySessionDto> searchTherapySessionsByPatientId(int patientId) throws Exception;

    List<TherapySessionDto> searchTherapySessionsByTherapistId(int therapistId) throws Exception;

}
