package com.Kuba2412.medicalclinic.service;

import com.Kuba2412.medicalclinic.model.mapper.InstitutionMapper;
import com.Kuba2412.medicalclinic.model.Doctor;
import com.Kuba2412.medicalclinic.model.Institution;
import com.Kuba2412.medicalclinic.model.dto.InstitutionDTO;
import com.Kuba2412.medicalclinic.repository.InstitutionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

    @Transactional
    public void createInstitution(InstitutionDTO institutionDTO) {
        Institution institution = institutionMapper.toInstitution(institutionDTO);
        institutionRepository.save(institution);
    }

    @Transactional
    public List<InstitutionDTO> getAllInstitutions(Pageable pageable) {
        List<Institution> institutions = institutionRepository.findAll(pageable).getContent();
        return institutions.stream()
                .map(institutionMapper::toInstitutionDTO)
                .toList();
    }

    public List<Doctor> getDoctorsForInstitution(Long institutionId) {
        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new IllegalArgumentException("Institution not found"));
        return institution.getDoctors();
    }
}




