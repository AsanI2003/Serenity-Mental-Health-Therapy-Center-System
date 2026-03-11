package com.project.serenity.bo.custom.impl;

import com.project.serenity.bo.custom.TherapyProgramBO;
import com.project.serenity.dao.DAOFactory;
import com.project.serenity.dao.custom.TherapyProgramDAO;
import com.project.serenity.dto.TherapyProgramDto;
import com.project.serenity.entity.TherapyProgram;

import java.util.ArrayList;
import java.util.List;

public class TherapyProgramBOImpl implements TherapyProgramBO {
     private final TherapyProgramDAO programDAO = (TherapyProgramDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPYPROGRAM);

    public void saveTherapyProgram(TherapyProgramDto dto) throws Exception {
        TherapyProgram program = new TherapyProgram();
        program.setName(dto.getName());
        program.setDuration(dto.getDuration());
        program.setFee(dto.getFee());
        programDAO.save(program);
    }

    public void updateTherapyProgram(TherapyProgramDto dto) throws Exception {
        TherapyProgram program = new TherapyProgram();
        program.setProgramId(dto.getProgramId());
        program.setName(dto.getName());
        program.setDuration(dto.getDuration());
        program.setFee(dto.getFee());
        programDAO.update(program);
    }

    public void deleteTherapyProgram(int id) throws Exception {
        programDAO.delete(id);
    }

    public List<TherapyProgramDto> getAllTherapyPrograms() throws Exception {
        List<TherapyProgram> all = programDAO.getAll();
        List<TherapyProgramDto> list = new ArrayList<>();
        for (TherapyProgram p : all) {
            list.add(new TherapyProgramDto(
                    p.getProgramId(),
                    p.getName(),
                    p.getDuration(),
                    p.getFee()
            ));
        }
        return list;
    }
}