package com.Kuba2412.medicalclinic.service;

import com.Kuba2412.medicalclinic.model.mapper.PatientMapper;
import com.Kuba2412.medicalclinic.model.dto.PatientDTO;
import com.Kuba2412.medicalclinic.model.User;
import com.Kuba2412.medicalclinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import com.Kuba2412.medicalclinic.model.Patient;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserService userService;
    private final PatientMapper patientMapper;

    public PatientDTO getPatientDtoByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        return patientMapper.patientToPatientDTO(patient);
    }
    public Patient getPatientByEmail(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
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

    public PatientDTO addPatient(String username, String password, PatientDTO patientDTO) {
        User user = userService.addUser(username, password);
        Patient patient = patientMapper.patientDTOToPatient(patientDTO);
        patient.setUser(user);
        patientRepository.save(patient);
        return patientMapper.patientToPatientDTO(patient);
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