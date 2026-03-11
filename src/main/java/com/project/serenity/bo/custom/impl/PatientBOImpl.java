package com.project.serenity.bo.custom.impl;

import com.project.serenity.bo.custom.PatientBO;
import com.project.serenity.dao.DAOFactory;
import com.project.serenity.dao.custom.PatientDAO;
import com.project.serenity.dto.PatientDto;
import com.project.serenity.entity.Patient;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PatientBOImpl implements PatientBO {

    PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PATIENT);

    public boolean savePatient(PatientDto dto) throws SQLException {
        Patient patient = new Patient();
        patient.setPatientId(dto.getPatientId());
        patient.setName(dto.getName());
        patient.setPhone(dto.getPhone());
        return patientDAO.save(patient);
    }

    public boolean updatePatient(PatientDto dto) throws SQLException {
        Patient patient = new Patient();
        patient.setPatientId(dto.getPatientId());
        patient.setName(dto.getName());
        patient.setPhone(dto.getPhone());
        return patientDAO.update(patient);
    }

    public boolean deletePatient(int id) throws SQLException {
        return patientDAO.delete(id);
    }

    public PatientDto getPatient(int id) throws SQLException {
        Patient p = patientDAO.get(id);
        return new PatientDto(p.getPatientId(), p.getName(), p.getPhone());
    }

    public List<PatientDto> getAllPatients() throws SQLException {
        List<Patient> list = patientDAO.getAll();
        return list.stream().map(p -> new PatientDto(p.getPatientId(), p.getName(), p.getPhone())).collect(Collectors.toList());
    }
}