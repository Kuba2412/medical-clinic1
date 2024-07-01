package com.Kuba2412.medicalclinic.model.mapper;

import com.Kuba2412.medicalclinic.model.Institution;
import com.Kuba2412.medicalclinic.model.dto.InstitutionDTO;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {

    InstitutionDTO toInstitutionDTO(Institution institution);

    Institution toInstitution(InstitutionDTO institutionDTO);
}
