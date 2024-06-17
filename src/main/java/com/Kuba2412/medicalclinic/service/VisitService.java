package com.Kuba2412.medicalclinic.service;

import com.Kuba2412.medicalclinic.model.mapper.VisitMapper;
import com.Kuba2412.medicalclinic.model.Patient;
import com.Kuba2412.medicalclinic.model.Visit;
import com.Kuba2412.medicalclinic.model.dto.VisitDTO;
import com.Kuba2412.medicalclinic.repository.PatientRepository;
import com.Kuba2412.medicalclinic.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final VisitMapper visitMapper;

    /**
     * Test case 1: Tworzenie wizyty

     * - Opis: Sprawdza, czy metoda createVisit tworzy wizytę poprawnie.
     * - Kroki:
     *   1. Utwórz obiekt VisitDTO z poprawnymi danymi wizyty.
     *   2. Wywołaj metodę createVisit z utworzonym obiektem VisitDTO.
     *   3. Pobierz wizytę z bazdy danych i porównaj, czy dane zostały poprawnie zapisane.
     * - Wynik: Wizyta jest dodana do bazy danych zgodnie z oczekiwaniami.
     */

    public void createVisit(VisitDTO visitDTO) {
        Visit visit = visitMapper.visitDTOToVisit(visitDTO);
        validateVisitDateTime(visit.getStartVisit());
        visitRepository.save(visit);
    }

    /**
     * Test case 1: Pobieranie wszystkich wizyt dla pacjenta z paginacją

     * - Opis: Sprawdza, czy metoda getAllVisitsForPatient zwraca listę wszystkich wizyt dla pacjenta z użyciem paginacji.
     * - Kroki:
     *   1. Utwórz kilka wizyt i dodaj je do bazy danych przypisując je do tego samego pacjenta.
     *   2. Utwórz obiekt Pageable z ustawioną paginacją (np. PageRequest.of(0, 10)).
     *   3. Wywołaj metodę getAllVisitsForPatient z identyfikatorem pacjenta i obiektem Pageable.
     *   4. Sprawdź czy lista instytucji zawiera oczekiwaną liczbę elementów (np. 10)
     * - Wynik: Metoda zwraca poprawnie paginowaną listę wizyt dla podanego pacjenta.
     */

    public List<Visit> getAllVisitsForPatient(Long patientId, Pageable pageable) {
        return visitRepository.findAllByPatientId(patientId, pageable).getContent();
    }

    /**
     * Test case 1: Rejestracja pacjenta na wizytę

     * - Opis: Sprawdza, czy metoda registerPatientForVisit rejestracji pacjenta na wizytę działa poprawnie.
     * - Kroki:
     *   1. Utwórz nową wizytę i dodaj ją do bazy danych.
     *   2. Utwórz nowego pacjenta i dodaj go do bazy danych.
     *   3. Wywołaj metodę registerPatientForVisit z identyfikatorem utworzonej wizyty i pacjenta.
     *   4. Sprawdź, czy pacjent został poprawnie zarejestrowany na wizytę.
     * - Wynik: Pacjent jest zarejestrowany na wizytę zgodnie z oczekiwaniami.
     */

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




