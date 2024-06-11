package com.Kuba2412.medicalclinic.service;

import com.Kuba2412.medicalclinic.model.mapper.DoctorMapper;
import com.Kuba2412.medicalclinic.model.mapper.SimpleDoctorDTO;
import com.Kuba2412.medicalclinic.model.Doctor;
import com.Kuba2412.medicalclinic.model.Institution;
import com.Kuba2412.medicalclinic.model.dto.DoctorDTO;
import com.Kuba2412.medicalclinic.repository.DoctorRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Transactional
    public void createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = doctorMapper.toDoctor(doctorDTO);
        doctorRepository.save(doctor);
    }

    @Transactional
    public List<SimpleDoctorDTO> getAllDoctors(Pageable pageable) {
        List<Doctor> doctors = doctorRepository.findAll(pageable).getContent();
        return doctors.stream()
                .map(doctorMapper::toSimpleDoctorDTO)
                .toList();
    }

    public Page<SimpleDoctorDTO> getAllSimpleDoctors(Pageable pageable) {
        return doctorRepository.findAll(pageable)
                .map(doctorMapper::toSimpleDoctorDTO);
        }

    @Transactional
    public List<Institution> getAssignedInstitutionsForDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        return doctor.getInstitutions();
    }
}
