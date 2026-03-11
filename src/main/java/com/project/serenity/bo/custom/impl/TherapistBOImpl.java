package com.project.serenity.bo.custom.impl;

import com.project.serenity.bo.custom.TherapistBO;
import com.project.serenity.dao.DAOFactory;
import com.project.serenity.dao.custom.TherapistDAO;
import com.project.serenity.dao.custom.impl.TherapistDAOImpl;
import com.project.serenity.dto.TherapistDto;
import com.project.serenity.entity.Therapist;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class TherapistBOImpl implements TherapistBO {

    private final TherapistDAO dao = (TherapistDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPIST);

    public void saveTherapist(TherapistDto dto) throws SQLException {
        Therapist t = new Therapist(dto.getTherapistId(), dto.getName(), dto.getPhone(), dto.getSpecialization(), dto.getAvailability(), null, null);
        dao.save(t);
    }

    public void updateTherapist(TherapistDto dto) throws SQLException {
        Therapist t = new Therapist(dto.getTherapistId(), dto.getName(), dto.getPhone(), dto.getSpecialization(), dto.getAvailability(), null, null);
        dao.update(t);
    }

    public void deleteTherapist(int id) throws SQLException {
        dao.delete(id);
    }

    public List<TherapistDto> getAllTherapists() throws SQLException {
        return dao.getAll().stream()
                .map(t -> new TherapistDto(t.getTherapistId(), t.getName(), t.getPhone(), t.getSpecialization(), t.getAvailability()))
                .collect(Collectors.toList());
    }
}