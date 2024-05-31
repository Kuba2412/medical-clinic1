package com.Kuba2412.medicalclinic.mapper;


import com.Kuba2412.medicalclinic.model.Visit;

import com.Kuba2412.medicalclinic.model.dto.VisitDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface VisitMapper {

    VisitDTO visitToVisitDTO(Visit visit);

    Visit visitDTOToVisit(VisitDTO visitDTO);

}
