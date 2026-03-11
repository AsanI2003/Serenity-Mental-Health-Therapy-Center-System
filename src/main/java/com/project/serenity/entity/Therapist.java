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
public class Therapist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int therapistId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,length = 10)
    private int phone;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private String availability;

    @ManyToMany(mappedBy = "therapists", fetch = FetchType.EAGER)
    private List<TherapyProgram> programs;

    @OneToMany(mappedBy = "therapist",cascade = CascadeType.ALL)
    private List<TherapySession> therapySessions;


}


