package com.Kuba2412.medicalclinic.test;

import com.Kuba2412.medicalclinic.model.Patient;
import com.Kuba2412.medicalclinic.model.Visit;
import com.Kuba2412.medicalclinic.model.mapper.VisitMapper;
import com.Kuba2412.medicalclinic.repository.PatientRepository;
import com.Kuba2412.medicalclinic.repository.VisitRepository;
import com.Kuba2412.medicalclinic.service.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VisitServiceTest {

    private VisitService visitService;
    private VisitRepository visitRepository;
    private PatientRepository patientRepository;
    private VisitMapper visitMapper;

    @BeforeEach
    void setup() {
        visitRepository = Mockito.mock(VisitRepository.class);
        patientRepository = Mockito.mock(PatientRepository.class);
        visitMapper = Mockito.mock(VisitMapper.class);
        visitService = new VisitService(visitRepository, patientRepository, visitMapper);
    }

    @Test
    void getAllVisitsForPatient_VisitsExist_VisitsReturned() {
        // given
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
        when(visitRepository.findAllByPatientId(patientId, pageable)).thenReturn(visitPage);

        // when
        List<Visit> result = visitService.getAllVisitsForPatient(patientId, pageable);

        // then
        assertNotNull(result);
        assertEquals(visits.size(), result.size());

        Visit resultVisit1 = result.get(0);
        assertEquals(visit1.getId(), resultVisit1.getId());
        assertEquals(visit1.getStartVisit(), resultVisit1.getStartVisit());
        assertEquals(visit1.getEndVisit(), resultVisit1.getEndVisit());

        Visit resultVisit2 = result.get(1);
        assertEquals(visit2.getId(), resultVisit2.getId());
        assertEquals(visit2.getStartVisit(), resultVisit2.getStartVisit());
        assertEquals(visit2.getEndVisit(), resultVisit2.getEndVisit());
    }

    @Test
    void registerPatientForVisit_ValidVisitAndPatient_PatientRegistered() {
        // given
        Long visitId = 1L;
        Long patientId = 1L;
        LocalDateTime futureVisitDate = LocalDateTime.of(2025, 1, 1, 10, 0);
        Visit visit = new Visit();
        visit.setStartVisit(futureVisitDate);
        Patient patient = new Patient();
        when(visitRepository.findById(visitId)).thenReturn(Optional.of(visit));
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        // when
        visitService.registerPatientForVisit(visitId, patientId);

        // then
        assertEquals(patient, visit.getPatient());
        verify(visitRepository, times(1)).save(visit);
    }

    @Test
    void registerPatientForVisit_NonExistentVisit_ThrowsException() {
        // given
        Long visitId = 12345L;
        Long patientId = 1L;
        when(visitRepository.findById(visitId)).thenReturn(Optional.empty());

        // when + then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> visitService.registerPatientForVisit(visitId, patientId));
        assertEquals("Visit not found", exception.getMessage());
        verify(visitRepository, times(1)).findById(visitId);
        verify(patientRepository, never()).findById(anyLong());
    }

    @Test
    void registerPatientForVisit_NonExistentPatient_ThrowsException() {
        // given
        Long visitId = 1L;
        Long patientId = 12345L;
        LocalDateTime futureVisitDate = LocalDateTime.of(2025, 1, 1, 10, 0);
        Visit visit = new Visit();
        visit.setStartVisit(futureVisitDate);
        when(visitRepository.findById(visitId)).thenReturn(Optional.of(visit));
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // when + then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> visitService.registerPatientForVisit(visitId, patientId));
        assertEquals("Patient not found", exception.getMessage());
        verify(visitRepository, times(1)).findById(visitId);
        verify(patientRepository, times(1)).findById(patientId);
    }
}