package com.Kuba2412.medicalclinic.repository;

import com.Kuba2412.medicalclinic.model.Visit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findAllByPatientId(Long patientId);
}
