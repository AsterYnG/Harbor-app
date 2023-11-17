package com.app.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public final class Freighter {
     Integer freighterId;
     Integer tax;
     Integer weightCost;
     Integer sizeCost;
     Integer fragileCost;
     String freighterName;

}
