package com.Kuba2412.medicalclinic.controller;


import com.Kuba2412.medicalclinic.model.mapper.DoctorMapper;
import com.Kuba2412.medicalclinic.model.mapper.SimpleDoctorDTO;
import com.Kuba2412.medicalclinic.model.Institution;
import com.Kuba2412.medicalclinic.model.dto.DoctorDTO;
import com.Kuba2412.medicalclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<SimpleDoctorDTO> getAllDoctors(@RequestParam int page,
                                               @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return doctorService.getAllDoctors(pageable);
    }


    @GetMapping("/simple")
    public Page<SimpleDoctorDTO> getAllSimpleDoctors(@RequestParam int page,
                                                     @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return doctorService.getAllSimpleDoctors(pageable);
    }

    @GetMapping("/{doctorId}/institutions")
    public List<Institution> getAssignedInstitutionsForDoctor(@PathVariable Long doctorId) {
        return doctorService.getAssignedInstitutionsForDoctor(doctorId);
    }
}



