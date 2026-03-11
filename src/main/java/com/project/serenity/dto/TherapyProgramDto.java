package com.project.serenity.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class TherapyProgramDto {

    private int programId;
    private String name;
    private String duration;
    private double fee;
}
