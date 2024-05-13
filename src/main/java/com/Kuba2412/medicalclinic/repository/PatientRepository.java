package com.Kuba2412.medicalclinic.repository;


import com.Kuba2412.medicalclinic.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class PatientRepository {

    private List<Patient> patients = new ArrayList<>();

    public List<Patient> getAllPatients() {
        return patients;
    }

    public Optional<Patient> getPatientByEmail(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst();
    }

    public Patient addPatient(Patient patient) {
        patients.add(patient);
        return patient;
    }

    public void deletePatientByEmail(String email) {
        patients.removeIf(patient -> patient.getEmail().equals(email));
    }

    public Patient updatePatientByEmail(String email, Patient updatedPatient) {
        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            if (patient.getEmail().equals(email)) {
                updatedPatient.setId(patient.getId());
                patients.set(i, updatedPatient);
                return updatedPatient;
            }
        }
        return null;
    }
}