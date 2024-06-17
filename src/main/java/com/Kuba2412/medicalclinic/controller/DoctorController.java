package com.Kuba2412.medicalclinic.controller;

import com.Kuba2412.medicalclinic.model.mapper.SimpleDoctorDTO;
import com.Kuba2412.medicalclinic.model.Institution;
import com.Kuba2412.medicalclinic.model.dto.DoctorDTO;
import com.Kuba2412.medicalclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createDoctor(@RequestBody DoctorDTO doctorDTO) {
        doctorService.createDoctor(doctorDTO);
        return "Doctor created successfully.";
    }

    @GetMapping
    public List<SimpleDoctorDTO> getAllDoctors(Pageable pageable) {
        return doctorService.getAllDoctors(pageable);
    }

    @GetMapping("/simple")
    public List<SimpleDoctorDTO> getAllSimpleDoctors(Pageable pageable) {
        return doctorService.getAllSimpleDoctors(pageable);
    }

    @GetMapping("/{doctorId}/institutions")
    public List<Institution> getAssignedInstitutionsForDoctor(@PathVariable Long doctorId) {
        return doctorService.getAssignedInstitutionsForDoctor(doctorId);
    }
}



