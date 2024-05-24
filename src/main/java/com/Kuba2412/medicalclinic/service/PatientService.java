package com.Kuba2412.medicalclinic.service;

import com.Kuba2412.medicalclinic.mapper.PatientMapper;
import com.Kuba2412.medicalclinic.dto.PatientDTO;
import com.Kuba2412.medicalclinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import com.Kuba2412.medicalclinic.model.Patient;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientDTO getPatientDtoByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        return patientMapper.patientToPatientDTO(patient);
    }

    public List<PatientDTO> getPatientDtosByFirstName(String firstName) {
        List<Patient> patients;
        if (firstName != null) {
            patients = patientRepository.findByFirstName(firstName);
        } else {
            patients = patientRepository.findAll();
        }
        return patients.stream()
                .map(patientMapper::patientToPatientDTO)
                .toList();
    }

    public Patient addPatient(Patient patient) {
        checkEmailIsAvailable(patient.getEmail());
        return patientRepository.save(patient);
    }

    public void deletePatientByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        patientRepository.delete(patient);
    }

    public PatientDTO updatePatientByEmail(String email, PatientDTO newPatientDto) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        Patient updatedPatient = patientMapper.patientDTOToPatient(newPatientDto);
        updatedPatient.setId(patient.getId());
        return patientMapper.patientToPatientDTO(patientRepository.save(updatedPatient));
    }

    public Patient updatePasswordByEmail(String email, String newPassword) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        patient.setPassword(newPassword);
        return patientRepository.save(patient);
    }

    private void checkEmailIsAvailable(String email) {
        if (patientRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }
    }
}