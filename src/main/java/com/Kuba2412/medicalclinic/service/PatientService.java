package com.Kuba2412.medicalclinic.service;
import lombok.RequiredArgsConstructor;
import com.Kuba2412.medicalclinic.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Kuba2412.medicalclinic.repository.PatientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.getAllPatients();
    }

    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.getPatientByEmail(email);
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.addPatient(patient);
    }

    public void deletePatientByEmail(String email) {
        patientRepository.deletePatientByEmail(email);
    }

    public Patient updatePatientByEmail(String email, Patient updatedPatient) {
        return patientRepository.updatePatientByEmail(email, updatedPatient);
    }
}