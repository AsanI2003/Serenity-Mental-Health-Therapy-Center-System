package com.project.serenity.bo.custom;

import com.project.serenity.bo.SuperBO;
import com.project.serenity.dto.TherapyProgramDto;

import java.util.List;

public interface TherapyProgramBO extends SuperBO {
    void saveTherapyProgram(TherapyProgramDto dto) throws Exception;
    void updateTherapyProgram(TherapyProgramDto dto) throws Exception;
    void deleteTherapyProgram(int id) throws Exception;
    List<TherapyProgramDto> getAllTherapyPrograms() throws Exception;
}