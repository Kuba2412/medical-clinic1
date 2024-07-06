package com.Kuba2412.medicalclinic.controller;


import com.Kuba2412.medicalclinic.model.Patient;
import com.Kuba2412.medicalclinic.model.dto.PatientDTO;
import com.Kuba2412.medicalclinic.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    private Patient patient;
    private PatientDTO patientDTO;

    @BeforeEach
    void setup() {
        patient = new Patient();

        patient.setEmail("kuba123@gmail.com");
        patient.setFirstName("Kuba");
        patient.setLastName("Ppp");

        patientDTO = new PatientDTO();

        patientDTO.setEmail("kuba123@gmail.com");
        patientDTO.setFirstName("Kuba");
        patientDTO.setLastName("Ppp");
    }

    @Test
    void getPatientByEmail_PatientExists_PatientReturned() throws Exception {
        when(patientService.getPatientDtoByEmail(anyString())).thenReturn(patientDTO);

        mockMvc.perform(get("/patients/kuba123@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(patientDTO.getEmail()));
    }

    @Test
    void getPatientByEmail_PatientNotFound_ThrowException() throws Exception {
        String nonExistentEmail = "kp123@gmail.com";

        when(patientService.getPatientDtoByEmail(nonExistentEmail))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found."));

        mockMvc.perform(get("/patients/{email}", nonExistentEmail)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Patient not found."));
    }

    @Test
    void getPatientsByFirstName_PatientExists_PatientReturned() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<PatientDTO> patients = List.of(patientDTO);

        when(patientService.getPatientDtosByFirstName(anyString(), any(Pageable.class))).thenReturn(patients);

        mockMvc.perform(get("/patients?firstName=Kuba")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(patients.size()));
    }

    @Test
    void addPatient_ValidInput_PatientAdded() throws Exception {
        Patient patient = new Patient();
        patient.setEmail("newpatient@gmail.com");
        patient.setFirstName("John");
        patient.setLastName("Doe");

        when(patientService.addPatient(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("newpatient@gmail.com"));
    }

    @Test
    void deletePatientByEmail_PatientExists_PatientDeleted() throws Exception {
        mockMvc.perform(delete("/patients/test@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updatePatientByEmail_PatientExists_PatientUpdated() throws Exception {
        when(patientService.updatePatientByEmail(anyString(), any(PatientDTO.class))).thenReturn(patientDTO);

        mockMvc.perform(put("/patients/test@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(patientDTO.getEmail()));
    }

    @Test
    void updatePatientByEmail_PatientNotFound_ThrowException() throws Exception {
        String nonExistentEmail = "kp123@gmail.com";
        PatientDTO patientDTO = new PatientDTO();

        when(patientService.updatePatientByEmail(nonExistentEmail, patientDTO))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found."));

        mockMvc.perform(put("/patients/{email}", nonExistentEmail)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Patient not found."));
    }
}