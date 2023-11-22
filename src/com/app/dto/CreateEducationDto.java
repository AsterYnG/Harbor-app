package com.app.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateEducationDto {
    String grade;
    String establishment;
    String educationSerialNumber;
}
