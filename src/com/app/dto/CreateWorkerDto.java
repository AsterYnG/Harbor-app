package com.app.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class CreateWorkerDto {
    LocalDate hiringDate;
    String position;
    String medSerialNumber;
    String passportSerialNumber;
    String educationSerialNumber;
    String employmentSerialNumber;
}
