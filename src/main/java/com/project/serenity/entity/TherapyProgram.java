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
public class TherapyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int programId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private double fee;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "therapist_program",
            joinColumns = @JoinColumn(name = "program_id"),
            inverseJoinColumns = @JoinColumn(name = "therapist_id"))
    private List<Therapist> therapists;

    @ManyToMany(mappedBy = "programs",fetch = FetchType.EAGER)
    private List<Patient> patients;

    @OneToMany(mappedBy = "program",cascade = CascadeType.ALL)
    private List<TherapySession> therapySessions;


}


