package com.app.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class CreatePassportDto {
    String fullName;
    LocalDate birthDate;
    String sex;
    String citizenship;
    String passportSerialNumber;
}
