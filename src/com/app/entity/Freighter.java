package com.app.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public final class Freighter {
     private Integer freighterId;
     private Integer tax;
     private Integer weightCost;
     private Integer sizeCost;
     private Integer fragileCost;
     private String freighterName;
}
