package com.project.serenity.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString




@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @Column(nullable = false)
    private double paidAmount;

    @Column(nullable = false)
    private double pendingAmount;

    @Column(nullable = false)
    private String paymentStatus;

    @OneToOne(mappedBy = "payment",cascade = CascadeType.ALL)
    private TherapySession therapySession;


}


