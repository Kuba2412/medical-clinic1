package com.Kuba2412.medicalclinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitDTO {

        private Long id;
        private LocalDateTime startVisit;
        private LocalDateTime endVisit;
        private Long patientId;
}
