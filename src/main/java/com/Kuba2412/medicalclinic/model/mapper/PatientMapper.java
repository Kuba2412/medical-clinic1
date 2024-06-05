package com.Kuba2412.medicalclinic.model.mapper;

import com.Kuba2412.medicalclinic.model.Patient;
import com.Kuba2412.medicalclinic.model.dto.PatientDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO patientToPatientDTO(Patient patient);

    Patient patientDTOToPatient(PatientDTO patientDTO);
}
