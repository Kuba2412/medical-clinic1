package com.Kuba2412.medicalclinic.test;

import com.Kuba2412.medicalclinic.model.Doctor;
import com.Kuba2412.medicalclinic.model.Institution;
import com.Kuba2412.medicalclinic.model.dto.InstitutionDTO;
import com.Kuba2412.medicalclinic.model.mapper.InstitutionMapper;
import com.Kuba2412.medicalclinic.repository.InstitutionRepository;
import com.Kuba2412.medicalclinic.service.InstitutionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InstitutionServiceTest {

    private InstitutionService institutionService;
    private InstitutionRepository institutionRepository;
    private InstitutionMapper institutionMapper;

    @BeforeEach
    void setup() {
        institutionRepository = Mockito.mock(InstitutionRepository.class);
        institutionMapper = Mockito.mock(InstitutionMapper.class);
        institutionService = new InstitutionService(institutionRepository, institutionMapper);
    }

    @Test
    void getAllInstitutions_InstitutionsExist_InstitutionsReturned() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        List<Institution> institutions = Arrays.asList(new Institution(), new Institution());
        Page<Institution> institutionPage = new PageImpl<>(institutions, pageable, institutions.size());
        when(institutionRepository.findAll(pageable)).thenReturn(institutionPage);

        // when
        List<InstitutionDTO> result = institutionService.getAllInstitutions(pageable);

        // then
        assertNotNull(result);
        assertEquals(institutions.size(), result.size());
        verify(institutionMapper, times(institutions.size())).toInstitutionDTO(any(Institution.class));
    }

    @Test
    void getDoctorsForInstitution_InstitutionExists_DoctorsReturned() {
        // given
        Long institutionId = 1L;
        Institution institution = new Institution();
        List<Doctor> doctors = Arrays.asList(new Doctor(), new Doctor());
        institution.setDoctors(doctors);
        when(institutionRepository.findById(institutionId)).thenReturn(Optional.of(institution));

        // when
        List<Doctor> result = institutionService.getDoctorsForInstitution(institutionId);

        // then
        assertNotNull(result);
        assertEquals(doctors.size(), result.size());
    }
}






