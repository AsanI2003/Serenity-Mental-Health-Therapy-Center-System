package com.project.serenity.dto;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class TherapistDto {

    private int therapistId;
    private String name;
    private int phone;
    private String specialization;
    private String availability;
}
