package com.Kuba2412.medicalclinic.service;

import com.Kuba2412.medicalclinic.mapper.DoctorMapper;
import com.Kuba2412.medicalclinic.mapper.SimpleDoctorDTO;
import com.Kuba2412.medicalclinic.model.Doctor;
import com.Kuba2412.medicalclinic.model.Institution;
import com.Kuba2412.medicalclinic.model.dto.DoctorDTO;
import com.Kuba2412.medicalclinic.repository.DoctorRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public void createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = doctorMapper.toDoctor(doctorDTO);
        doctorRepository.save(doctor);
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toDoctorDTO)
                .toList();
    }


    public List<SimpleDoctorDTO> getAllSimpleDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toSimpleDoctorDTO)
                .toList();
    }

    public List<Institution> getAssignedInstitutionsForDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        return doctor.getInstitutions();
    }
}


