package com.project.serenity.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PatientDto {

    private int patientId;
    private String name;
    private String phone;
}
