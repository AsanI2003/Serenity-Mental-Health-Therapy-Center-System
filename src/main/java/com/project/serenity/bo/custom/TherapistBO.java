package com.project.serenity.bo.custom;

import com.project.serenity.bo.SuperBO;
import com.project.serenity.dto.TherapistDto;

import java.sql.SQLException;
import java.util.List;

public interface TherapistBO extends SuperBO {
    void saveTherapist(TherapistDto dto) throws SQLException;
    void updateTherapist(TherapistDto dto) throws SQLException;
    void deleteTherapist(int id) throws SQLException;
    List<TherapistDto> getAllTherapists() throws SQLException;
}