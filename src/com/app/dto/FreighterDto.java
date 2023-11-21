package com.app.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Value;


@Value
@Builder
public class FreighterDto {
     Integer freighterId;
     Integer weightCost;
     String freighterName;
}
