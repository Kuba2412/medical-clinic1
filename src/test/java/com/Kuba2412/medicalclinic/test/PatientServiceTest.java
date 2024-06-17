package com.Kuba2412.medicalclinic.test;

import com.Kuba2412.medicalclinic.model.Patient;

import com.Kuba2412.medicalclinic.model.dto.PatientDTO;
import com.Kuba2412.medicalclinic.model.mapper.PatientMapper;
import com.Kuba2412.medicalclinic.repository.PatientRepository;

import com.Kuba2412.medicalclinic.service.PatientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class PatientServiceTest {

    private PatientService patientService;
    private PatientRepository patientRepository;
    private PatientMapper patientMapper;

    @BeforeEach
    void setup() {
        patientRepository = Mockito.mock(PatientRepository.class);
        patientMapper = Mappers.getMapper(PatientMapper.class);
        patientService = new PatientService(patientRepository, patientMapper);
    }

    @Test
    void getPatientDtoByEmail_PatientExists_PatientDtoReturned() {
        // given
        String patientEmail = "patient@gmail.com";
        Patient patient = createPatient(patientEmail);
        when(patientRepository.findByEmail(anyString())).thenReturn(Optional.of(patient));

        // when
        PatientDTO result = patientService.getPatientDtoByEmail(patientEmail);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(patientEmail, result.getEmail());
    }

    @Test
    void getPatientDtosByFirstName_PatientsExist_PatientDtosReturned() {
        // given
        String firstName = "Kuba";
        Pageable pageable = PageRequest.of(0, 10);
        List<Patient> patients = Arrays.asList(createPatient("kuba1@gmail.com"), createPatient("kuba2@gmail.com"));
        Page<Patient> patientPage = new PageImpl<>(patients, pageable, patients.size());
        when(patientRepository.findByFirstName(firstName, pageable)).thenReturn(patientPage);

        // when
        List<PatientDTO> result = patientService.getPatientDtosByFirstName(firstName, pageable);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("john1@example.com", result.get(0).getEmail());
        Assertions.assertEquals("john2@example.com", result.get(1).getEmail());
    }

    @Test
    void addPatient_ValidPatient_PatientSaved() {
        // given
        Patient patient = createPatient("newpatient@gmail.com");
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        // when
        Patient result = patientService.addPatient(patient);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("newpatient@gmail.com", result.getEmail());
    }

    @Test
    void updatePatientByEmail_PatientExists_PatientUpdated() {
        // given
        String patientEmail = "updatepatient@gmail.com";
        Patient patient = createPatient(patientEmail);
        PatientDTO newPatientDto = new PatientDTO();
        newPatientDto.setEmail(patientEmail);
        newPatientDto.setFirstName("UpdatedFirstName");
        newPatientDto.setLastName("UpdatedLastName");
        newPatientDto.setPhoneNumber("123456789");
        when(patientRepository.findByEmail(anyString())).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        // when
        PatientDTO result = patientService.updatePatientByEmail(patientEmail, newPatientDto);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("UpdatedFirstName", result.getFirstName());
        Assertions.assertEquals("UpdatedLastName", result.getLastName());
        Assertions.assertEquals("123456789", result.getPhoneNumber());
    }

    private Patient createPatient(String email) {
        return new Patient(1L, email, "password", "Kuba", "Pp", "1234567890", LocalDate.of(2001, 1, 1), null, null);
    }
}