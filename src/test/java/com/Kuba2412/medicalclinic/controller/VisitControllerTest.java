package com.Kuba2412.medicalclinic.controller;

import com.Kuba2412.medicalclinic.model.Visit;
import com.Kuba2412.medicalclinic.model.dto.VisitDTO;
import com.Kuba2412.medicalclinic.repository.PatientRepository;
import com.Kuba2412.medicalclinic.repository.VisitRepository;
import com.Kuba2412.medicalclinic.service.VisitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VisitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VisitService visitService;

    @MockBean
    private VisitRepository visitRepository;

    @MockBean
    private PatientRepository patientRepository;

    @Test
    void createVisit_ValidInput_VisitCreatedSuccessfully() throws Exception {
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setPatientId(1L);
        visitDTO.setStartVisit(LocalDateTime.now());
        visitDTO.setEndVisit(LocalDateTime.now().plusHours(1));

        mockMvc.perform(post("/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(visitDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Visit created successfully."));
    }

    @Test
    void getAllVisitsForPatient_VisitsExist_VisitsReturned() throws Exception {
        Long patientId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        LocalDateTime startDate1 = LocalDateTime.of(2025, 1, 1, 9, 0);
        LocalDateTime endDate1 = startDate1.plusHours(1);

        LocalDateTime startDate2 = LocalDateTime.of(2025, 1, 1, 14, 0);
        LocalDateTime endDate2 = startDate2.plusHours(2);

        Visit visit1 = new Visit();
        visit1.setId(1L);
        visit1.setStartVisit(startDate1);
        visit1.setEndVisit(endDate1);

        Visit visit2 = new Visit();
        visit2.setId(2L);
        visit2.setStartVisit(startDate2);
        visit2.setEndVisit(endDate2);

        List<Visit> visits = Arrays.asList(visit1, visit2);
        Page<Visit> visitPage = new PageImpl<>(visits, pageable, visits.size());

        when(patientRepository.existsById(patientId)).thenReturn(true);
        when(visitRepository.findAllByPatientId(patientId, pageable)).thenReturn(visitPage);
        when(visitService.getAllVisitsForPatient(patientId, pageable)).thenReturn(visits);


        mockMvc.perform(get("/visits/patient/{patientId}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(visits.size()))
                .andExpect(jsonPath("$[0].id").value(visit1.getId()))
                .andExpect(jsonPath("$[1].id").value(visit2.getId()));
    }

    @Test
    void registerPatientForVisit_ValidInput_PatientRegisteredSuccessfully() throws Exception {
        Long visitId = 1L;
        Long patientId = 1L;

        mockMvc.perform(post("/visits/{visitId}/patient/{patientId}/register", visitId, patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Patient registered for visit successfully."));
    }

    @Test
    void registerPatientForVisit_VisitNotFound_ThrowException() throws Exception {
        Long nonExistentVisitId = 12345L;
        Long patientId = 1L;

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found."))
                .when(visitService).registerPatientForVisit(nonExistentVisitId, patientId);

        mockMvc.perform(post("/visits/{visitId}/patient/{patientId}/register", nonExistentVisitId, patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Visit not found."));
    }
}