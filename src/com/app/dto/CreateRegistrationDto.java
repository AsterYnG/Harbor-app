package com.app.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateRegistrationDto {
    String region;
    String city;
    String street;
    String house;
    Integer flat;
}
