package com.Kuba2412.medicalclinic.controller;

import com.Kuba2412.medicalclinic.model.Patient;
import com.Kuba2412.medicalclinic.model.dto.PatientDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.Kuba2412.medicalclinic.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/{email}")
    public PatientDTO getPatientByEmail(@PathVariable String email) {
        return patientService.getPatientDtoByEmail(email);
    }

    @GetMapping
    public List<PatientDTO> getPatientsByFirstName(@RequestParam  String firstName, Pageable pageable) {
        return patientService.getPatientDtosByFirstName(firstName, pageable);
    }

    @PostMapping
    public Patient addPatient(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatientByEmail(@PathVariable("email") String email) {
        patientService.deletePatientByEmail(email);
    }

    @PutMapping("/{email}")
    public PatientDTO updatePatientByEmail(@PathVariable("email") String email, @RequestBody PatientDTO patientDto) {
        return patientService.updatePatientByEmail(email, patientDto);
    }

}

