package com.Kuba2412.medicalclinic.controller;


import com.Kuba2412.medicalclinic.model.mapper.InstitutionMapper;
import com.Kuba2412.medicalclinic.model.Doctor;
import com.Kuba2412.medicalclinic.model.dto.InstitutionDTO;
import com.Kuba2412.medicalclinic.service.InstitutionService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/institutions")
public class InstitutionController {

    private final InstitutionService institutionService;
    private final InstitutionMapper institutionMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createInstitution(@RequestBody InstitutionDTO institutionDTO) {
        institutionService.createInstitution(institutionDTO);
        return "Institution created successfully.";
    }

    @GetMapping
    public List<InstitutionDTO> getAllInstitutions() {
        return institutionService.getAllInstitutions();
    }

    @GetMapping("/{institutionId}/doctors")
    public List<Doctor> getDoctorsForInstitution(@PathVariable Long institutionId) {
        return institutionService.getDoctorsForInstitution(institutionId);
    }
}


