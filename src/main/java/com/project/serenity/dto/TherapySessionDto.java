package com.project.serenity.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TherapySessionDto {

    private int sessionId;
    private LocalDateTime bookedat;
    private String duration;
    private int therapistId;
    private int patientId;
    private int programId;
    private int paymentId;
}
