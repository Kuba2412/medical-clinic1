package com.Kuba2412.medicalclinic.service;

import com.Kuba2412.medicalclinic.model.mapper.InstitutionMapper;
import com.Kuba2412.medicalclinic.model.Doctor;
import com.Kuba2412.medicalclinic.model.Institution;
import com.Kuba2412.medicalclinic.model.dto.InstitutionDTO;
import com.Kuba2412.medicalclinic.repository.InstitutionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<InstitutionDTO> getAllInstitutions(Pageable pageable) {
        return institutionRepository.findAll(pageable)
                .map(institutionMapper::toInstitutionDTO);
    }

    public List<Doctor> getDoctorsForInstitution(Long institutionId) {
        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new IllegalArgumentException("Institution not found"));
        return institution.getDoctors();
    }
}




