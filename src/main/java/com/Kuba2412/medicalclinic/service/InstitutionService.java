package com.Kuba2412.medicalclinic.service;

import com.Kuba2412.medicalclinic.mapper.InstitutionMapper;
import com.Kuba2412.medicalclinic.model.Doctor;
import com.Kuba2412.medicalclinic.model.Institution;
import com.Kuba2412.medicalclinic.model.dto.InstitutionDTO;

import com.Kuba2412.medicalclinic.repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

    public void createInstitution(InstitutionDTO institutionDTO) {
        Institution institution = institutionMapper.toInstitution(institutionDTO);
        institutionRepository.save(institution);
    }

    public List<InstitutionDTO> getAllInstitutions() {
        return institutionRepository.findAll().stream()
                .map(institutionMapper::toInstitutionDTO)
                .toList();
    }

    public List<Doctor> getDoctorsForInstitution(Long institutionId) {
        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new IllegalArgumentException("Institution not found"));
        return institution.getDoctors();
    }
}




