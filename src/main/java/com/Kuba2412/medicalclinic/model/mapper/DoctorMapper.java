package com.Kuba2412.medicalclinic.model.mapper;

import com.Kuba2412.medicalclinic.model.Doctor;

import com.Kuba2412.medicalclinic.model.dto.DoctorDTO;

import org.mapstruct.Mapper;

import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface DoctorMapper {

    DoctorDTO toDoctorDTO(Doctor doctor);

    Doctor toDoctor(DoctorDTO doctorDTO);

    SimpleDoctorDTO toSimpleDoctorDTO(Doctor doctor);

}

