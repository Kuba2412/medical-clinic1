package com.Kuba2412.medicalclinic.repository;


import com.Kuba2412.medicalclinic.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}
