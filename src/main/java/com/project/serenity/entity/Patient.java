package com.project.serenity.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString




@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int patientId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 10)
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "patient_program",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "program_id"))
    private List<TherapyProgram> programs;

    @OneToMany(mappedBy = "patient")
    private List<TherapySession> therapySessions;


}


