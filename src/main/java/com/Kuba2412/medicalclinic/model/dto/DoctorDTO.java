package com.Kuba2412.medicalclinic.model.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    private Long id;
    private String email;
    @NotNull(message = "First name cannot be null")
    private String firstName;
    @NotNull(message = "Last name cannot be null")
    private String lastName;
    @NotNull(message = "Specialization cannot be null")
    private String specialization;
    private List<Long> institutionIds;
}
