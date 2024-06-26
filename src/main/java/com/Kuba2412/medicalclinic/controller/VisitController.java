package com.Kuba2412.medicalclinic.controller;


import com.Kuba2412.medicalclinic.model.Visit;

import com.Kuba2412.medicalclinic.model.dto.VisitDTO;
import com.Kuba2412.medicalclinic.service.VisitService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createVisit(@RequestBody VisitDTO visitDTO) {
        visitService.createVisit(visitDTO);
        return "Visit created successfully.";
    }

    @GetMapping("/patient/{patientId}")
    public List<Visit> getAllVisitsForPatient(@PathVariable Long patientId, Pageable pageable) {
        return visitService.getAllVisitsForPatient(patientId, pageable);
    }

    @PostMapping("/{visitId}/patient/{patientId}/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerPatientForVisit(@PathVariable Long visitId, @PathVariable Long patientId) {
        visitService.registerPatientForVisit(visitId, patientId);
        return "Patient registered for visit successfully.";
    }
}