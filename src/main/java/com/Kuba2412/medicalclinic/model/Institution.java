package com.Kuba2412.medicalclinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String city;
    private String postalCode;
    private String street;
    private String buildingNumber;

    @ManyToMany(mappedBy = "institutions")
    private List<Doctor> doctors;
}
