package com.project.serenity.bo.custom;

import com.project.serenity.bo.SuperBO;
import com.project.serenity.dto.PatientDto;

import java.sql.SQLException;
import java.util.List;

public interface PatientBO  extends SuperBO {
    boolean savePatient(PatientDto dto) throws SQLException;
    boolean updatePatient(PatientDto dto) throws SQLException;
    boolean deletePatient(int id) throws SQLException;
    PatientDto getPatient(int id) throws SQLException;
    List<PatientDto> getAllPatients() throws SQLException;
}