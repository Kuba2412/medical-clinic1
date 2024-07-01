package com.Kuba2412.medicalclinic.repository;

import com.Kuba2412.medicalclinic.model.Visit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    Page<Visit> findAllByPatientId(Long patientId, Pageable pageable);
}
