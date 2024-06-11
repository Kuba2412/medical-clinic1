package com.Kuba2412.medicalclinic.service;

import com.Kuba2412.medicalclinic.model.mapper.PatientMapper;
import com.Kuba2412.medicalclinic.model.dto.PatientDTO;
import com.Kuba2412.medicalclinic.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.Kuba2412.medicalclinic.model.Patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    public List<PatientDTO> getPatientDtosByFirstName(String firstName, Pageable pageable) {
        Page<Patient> patientsPage;
        if (firstName != null) {
            patientsPage = patientRepository.findByFirstName(firstName, pageable);
        } else {
            patientsPage = patientRepository.findAll(pageable);
        }
        return patientsPage.getContent().stream()
                .map(patientMapper::patientToPatientDTO)
                .toList();
    }

    public Patient addPatient(Patient patient) {
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
}
