package com.Kuba2412.medicalclinic.controller;


import com.Kuba2412.medicalclinic.model.mapper.DoctorMapper;
import com.Kuba2412.medicalclinic.model.mapper.SimpleDoctorDTO;
import com.Kuba2412.medicalclinic.model.Institution;
import com.Kuba2412.medicalclinic.model.dto.DoctorDTO;

import com.Kuba2412.medicalclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createDoctor(@RequestBody DoctorDTO doctorDTO) {
        doctorService.createDoctor(doctorDTO);
        return "Doctor created successfully.";
    }

    @GetMapping
    public List<DoctorDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/simple")
    public List<SimpleDoctorDTO> getAllSimpleDoctors() {
        return doctorService.getAllSimpleDoctors();
    }

    @GetMapping("/{doctorId}/institutions")
    public List<Institution> getAssignedInstitutionsForDoctor(@PathVariable Long doctorId) {
        return doctorService.getAssignedInstitutionsForDoctor(doctorId);
    }
}



