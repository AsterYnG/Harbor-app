package com.app.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class WorkerDto {
     LocalDate hiringDate;
    String position;
    String medSerialNumber;
    String passportSerialNumber;
    String educationSerialNumber;
    String employmentSerialNumber;
    String fullName;
}
