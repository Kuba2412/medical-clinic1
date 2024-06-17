package com.Kuba2412.medicalclinic.controller;

import com.Kuba2412.medicalclinic.model.Doctor;
import com.Kuba2412.medicalclinic.model.dto.InstitutionDTO;
import com.Kuba2412.medicalclinic.repository.InstitutionRepository;
import com.Kuba2412.medicalclinic.service.InstitutionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institutions")
public class InstitutionController {

    private final InstitutionService institutionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createInstitution(@RequestBody InstitutionDTO institutionDTO) {
        institutionService.createInstitution(institutionDTO);
        return "Institution created successfully.";
    }

    @GetMapping("/institutions")
    public List<InstitutionDTO> getAllInstitutions(Pageable pageable ) {
        return institutionService.getAllInstitutions(pageable);
    }

    @GetMapping("/{institutionId}/doctors")
    public List<Doctor> getDoctorsForInstitution(@PathVariable Long institutionId) {
        return institutionService.getDoctorsForInstitution(institutionId);
    }
}


