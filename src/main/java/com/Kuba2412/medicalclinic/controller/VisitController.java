package com.Kuba2412.medicalclinic.controller;


import com.Kuba2412.medicalclinic.model.Visit;

import com.Kuba2412.medicalclinic.model.dto.VisitDTO;
import com.Kuba2412.medicalclinic.service.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Create a new visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Visit created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public String createVisit(@RequestBody VisitDTO visitDTO) {
        visitService.createVisit(visitDTO);
        return "Visit created successfully.";
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get all visits for a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of visits returned successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public List<Visit> getAllVisitsForPatient(@PathVariable Long patientId, Pageable pageable) {
        return visitService.getAllVisitsForPatient(patientId, pageable);
    }

    @PostMapping("/{visitId}/patient/{patientId}/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a patient for a visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient registered for visit successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Visit or Patient not found")
    })
    public String registerPatientForVisit(@PathVariable Long visitId, @PathVariable Long patientId) {
        visitService.registerPatientForVisit(visitId, patientId);
        return "Patient registered for visit successfully.";
    }
}