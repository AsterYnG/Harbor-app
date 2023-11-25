package com.app.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateCargoDto {
    Integer cargoWeight;
    Boolean isFragile;
    Integer cargoSize;
    String destination;
}
