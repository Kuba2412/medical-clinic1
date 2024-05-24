package com.Kuba2412.medicalclinic.repository;

import com.Kuba2412.medicalclinic.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);

    List<Patient> findByFirstName(String firstName);

}
