package com.project.serenity.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDto {
    private int paymentId;
    private double paidAmount;
    private double pendingAmount;
    private String paymentStatus;
}
