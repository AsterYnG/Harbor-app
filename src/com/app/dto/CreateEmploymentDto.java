package com.app.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CreateEmploymentDto {
    String employmentSerialNumber;
    Integer experience;
    String previousJob;
}
