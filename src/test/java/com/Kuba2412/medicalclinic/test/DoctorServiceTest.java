package com.Kuba2412.medicalclinic.test;

import com.Kuba2412.medicalclinic.model.Doctor;
import com.Kuba2412.medicalclinic.model.Institution;
import com.Kuba2412.medicalclinic.model.mapper.DoctorMapper;
import com.Kuba2412.medicalclinic.model.mapper.SimpleDoctorDTO;
import com.Kuba2412.medicalclinic.repository.DoctorRepository;
import com.Kuba2412.medicalclinic.service.DoctorService;
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

public class DoctorServiceTest {

    private DoctorService doctorService;
    private DoctorRepository doctorRepository;
    private DoctorMapper doctorMapper;

    @BeforeEach
    void setup() {
        doctorRepository = Mockito.mock(DoctorRepository.class);
        doctorMapper = Mockito.mock(DoctorMapper.class);
        doctorService = new DoctorService(doctorRepository, doctorMapper);
    }

    @Test
    void getAllDoctors_DoctorsExist_DoctorsReturned() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        List<Doctor> doctors = Arrays.asList(new Doctor(), new Doctor());
        Page<Doctor> doctorPage = new PageImpl<>(doctors, pageable, doctors.size());
        when(doctorRepository.findAll(pageable)).thenReturn(doctorPage);

        // when
        List<SimpleDoctorDTO> result = doctorService.getAllDoctors(pageable);

        // then
        assertNotNull(result);
        assertEquals(doctors.size(), result.size());
        verify(doctorMapper, times(doctors.size())).toSimpleDoctorDTO(any(Doctor.class));
    }

    @Test
    void getAllSimpleDoctors_DoctorsExist_DoctorsReturned() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        List<Doctor> doctors = Arrays.asList(new Doctor(), new Doctor());
        Page<Doctor> doctorPage = new PageImpl<>(doctors, pageable, doctors.size());
        when(doctorRepository.findAll(pageable)).thenReturn(doctorPage);

        // when
        List<SimpleDoctorDTO> result = doctorService.getAllSimpleDoctors(pageable);

        // then
        assertNotNull(result);
        assertEquals(doctors.size(), result.size());
        verify(doctorMapper, times(doctors.size())).toSimpleDoctorDTO(any(Doctor.class));
    }

    @Test
    void getAssignedInstitutionsForDoctor_DoctorExists_InstitutionsReturned() {
        // given
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        List<Institution> institutions = Arrays.asList(new Institution(), new Institution());
        doctor.setInstitutions(institutions);
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        // when
        List<Institution> result = doctorService.getAssignedInstitutionsForDoctor(doctorId);

        // then
        assertNotNull(result);
        assertEquals(institutions.size(), result.size());
    }
}





