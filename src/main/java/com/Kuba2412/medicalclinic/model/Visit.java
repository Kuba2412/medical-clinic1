package com.Kuba2412.medicalclinic.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startVisit;

    private LocalDateTime endVisit;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
