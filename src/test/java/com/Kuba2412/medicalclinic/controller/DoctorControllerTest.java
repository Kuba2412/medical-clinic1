package com.Kuba2412.medicalclinic.controller;

import com.Kuba2412.medicalclinic.model.Institution;
import com.Kuba2412.medicalclinic.model.dto.DoctorDTO;
import com.Kuba2412.medicalclinic.model.mapper.SimpleDoctorDTO;
import com.Kuba2412.medicalclinic.service.DoctorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DoctorService doctorService;

    private DoctorDTO doctorDTO;
    private SimpleDoctorDTO simpleDoctorDTO;
    private Institution institution;

    @BeforeEach
    void setup() {
        doctorDTO = new DoctorDTO();
        doctorDTO.setFirstName("Kuba");
        doctorDTO.setLastName("Ppp");
        doctorDTO.setEmail("kuba123@gmail.com");

        simpleDoctorDTO = new SimpleDoctorDTO();
        simpleDoctorDTO.setFirstName("Kuba");
        simpleDoctorDTO.setLastName("Ppp");

        institution = new Institution();
        institution.setId(1L);
        institution.setName("Szpital 1");
    }

    @Test
    void createDoctor_ValidInput_DoctorCreated() throws Exception {
        mockMvc.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Doctor created successfully."));
    }

    @Test
    void getAllDoctors_DoctorsExist_DoctorsReturned() throws Exception {

        SimpleDoctorDTO doctorDTO = new SimpleDoctorDTO();
        doctorDTO.setFirstName("Kuba");
        doctorDTO.setLastName("Ppp");
        List<SimpleDoctorDTO> doctors = List.of(doctorDTO);

        when(doctorService.getAllDoctors(any())).thenReturn(doctors);

        mockMvc.perform(get("/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(doctors.size()))
                .andExpect(jsonPath("$[0].firstName").value("Kuba"))
                .andExpect(jsonPath("$[0].lastName").value("Ppp"));
    }

    @Test
    void getAllSimpleDoctors_DoctorsExist_DoctorsReturned() throws Exception {
        List<SimpleDoctorDTO> doctors = List.of(simpleDoctorDTO);
        when(doctorService.getAllSimpleDoctors(any())).thenReturn(doctors);

        mockMvc.perform(get("/doctors/simple")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(doctors.size()))
                .andExpect(jsonPath("$[0].firstName").value("Kuba"))
                .andExpect(jsonPath("$[0].lastName").value("Ppp"));

        verify(doctorService, times(1)).getAllSimpleDoctors(any());
    }

    @Test
    void getAssignedInstitutionsForDoctor_DoctorExists_InstitutionsReturned() throws Exception {
        List<Institution> institutions = List.of(institution);
        when(doctorService.getAssignedInstitutionsForDoctor(anyLong())).thenReturn(institutions);

        mockMvc.perform(get("/doctors/1/institutions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(institutions.size()))
                .andExpect(jsonPath("$[0].name").value(institution.getName()));

        verify(doctorService, times(1)).getAssignedInstitutionsForDoctor(1L);
    }

    @Test
    void getAssignedInstitutionsForDoctor_DoctorNotFound_ThrowException() throws Exception {
        when(doctorService.getAssignedInstitutionsForDoctor(anyLong()))
                .thenThrow(new IllegalArgumentException("Doctor not found."));

        mockMvc.perform(get("/doctors/12345/institutions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Doctor not found."));
    }
}

