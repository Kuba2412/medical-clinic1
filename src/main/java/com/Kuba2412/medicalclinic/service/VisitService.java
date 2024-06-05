package com.Kuba2412.medicalclinic.service;

import com.Kuba2412.medicalclinic.model.mapper.VisitMapper;
import com.Kuba2412.medicalclinic.model.Patient;
import com.Kuba2412.medicalclinic.model.Visit;
import com.Kuba2412.medicalclinic.model.dto.VisitDTO;
import com.Kuba2412.medicalclinic.repository.PatientRepository;
import com.Kuba2412.medicalclinic.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final VisitMapper visitMapper;

    public void createVisit(VisitDTO visitDTO) {
        Visit visit = visitMapper.visitDTOToVisit(visitDTO);
        validateVisitDateTime(visit.getStartVisit());
        visitRepository.save(visit);
    }

    public List<Visit> getAllVisitsForPatient(Long patientId) {
        return visitRepository.findAllByPatientId(patientId);
    }

    public void registerPatientForVisit(Long visitId, Long patientId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found"));

        if (visit.getStartVisit().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot register for past visit.");
        }

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        if (visit.getPatient() != null) {
            throw new IllegalArgumentException("Visit already has registered patient.");
        }


        visit.setPatient(patient);
        visitRepository.save(visit);

    }

    private void validateVisitDateTime(LocalDateTime dateTime) {
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot create visit for past date.");
        }
        if (dateTime.getMinute() % 15 != 0) {
            throw new IllegalArgumentException("Visit time must be in full quarter-hour intervals.");
        }
    }
}




