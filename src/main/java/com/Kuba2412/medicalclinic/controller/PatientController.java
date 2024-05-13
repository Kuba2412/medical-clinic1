package com.Kuba2412.medicalclinic.controller;

import lombok.RequiredArgsConstructor;
import com.Kuba2412.medicalclinic.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Kuba2412.medicalclinic.service.PatientService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{email}")
    public Optional<Patient> getPatientByEmail(@PathVariable String email) {
        return patientService.getPatientByEmail(email);
    }

    @PostMapping
    public Patient addPatient(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @DeleteMapping("/{email}")
    public void deletePatientByEmail(@PathVariable String email) {
        patientService.deletePatientByEmail(email);
    }

    @PutMapping("/{email}")
    public Patient updatePatientByEmail(@PathVariable String email, @RequestBody Patient patient) {
        return patientService.updatePatientByEmail(email, patient);
    }
}

