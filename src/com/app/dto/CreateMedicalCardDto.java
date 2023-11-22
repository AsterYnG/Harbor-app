package com.app.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateMedicalCardDto {
    Boolean hivStatus;
    String illness;
    String medSerialNumber;
}
