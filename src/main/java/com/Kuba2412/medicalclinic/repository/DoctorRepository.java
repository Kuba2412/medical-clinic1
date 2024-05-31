package com.Kuba2412.medicalclinic.repository;

import com.Kuba2412.medicalclinic.model.Doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
