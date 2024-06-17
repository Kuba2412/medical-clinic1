package com.Kuba2412.medicalclinic.test;

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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

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
        List<Visit> visits = Arrays.asList(new Visit(), new Visit());
        Page<Visit> visitPage = new PageImpl<>(visits, pageable, visits.size());
        when(visitRepository.findAllByPatientId(patientId, pageable)).thenReturn(visitPage);

        // when
        List<Visit> result = visitService.getAllVisitsForPatient(patientId, pageable);

        // then
        assertNotNull(result);
        assertEquals(visits.size(), result.size());
    }
}
