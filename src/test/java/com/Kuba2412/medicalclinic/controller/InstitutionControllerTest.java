package com.Kuba2412.medicalclinic.controller;

import com.Kuba2412.medicalclinic.model.Doctor;
import com.Kuba2412.medicalclinic.model.dto.InstitutionDTO;
import com.Kuba2412.medicalclinic.service.InstitutionService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InstitutionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InstitutionService institutionService;

    private InstitutionDTO institutionDTO;
    private Doctor doctor;

    @BeforeEach
    void setup() {
        institutionDTO = new InstitutionDTO();
        institutionDTO.setName("Szpital 1");

        doctor = new Doctor();
        doctor.setFirstName("Kuba");
        doctor.setLastName("Ppp");
    }

    @Test
    void createInstitution_ValidInput_InstitutionCreated() throws Exception {
        mockMvc.perform(post("/institutions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(institutionDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Institution created successfully."));
    }

    @Test
    void createInstitution_InvalidInput_BadRequest() throws Exception {
        InstitutionDTO invalidInstitutionDTO = new InstitutionDTO();

        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input")).when(institutionService).createInstitution(any(InstitutionDTO.class));

        mockMvc.perform(post("/institutions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidInstitutionDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllInstitutions_InstitutionsExist_InstitutionsReturned() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<InstitutionDTO> institutions = Arrays.asList(institutionDTO);
        when(institutionService.getAllInstitutions(any(Pageable.class))).thenReturn(institutions);

        mockMvc.perform(get("/institutions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(institutions.size()));
    }

    @Test
    void getDoctorsForInstitution_InstitutionExists_DoctorsReturned() throws Exception {
        Long institutionId = 1L;
        List<Doctor> doctors = Arrays.asList(doctor);

        when(institutionService.getDoctorsForInstitution(institutionId)).thenReturn(doctors);

        mockMvc.perform(get("/institutions/" + institutionId + "/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(doctors.size()))
                .andExpect(jsonPath("$[0].firstName").value(doctor.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(doctor.getLastName()));
    }

    @Test
    void getDoctorsForInstitution_InstitutionNotFound_ThrowException() throws Exception {
        Long nonExistentInstitutionId = 12345L;

        when(institutionService.getDoctorsForInstitution(nonExistentInstitutionId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Institution not found."));

        mockMvc.perform(get("/institutions/{institutionId}/doctors", nonExistentInstitutionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Institution not found."));
    }
}