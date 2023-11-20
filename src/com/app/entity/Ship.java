package com.app.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ship {
    private Integer shipId;
    private Boolean inUse;
    private Team teamId;
    private ShipModel shipModel;
    private Freighter freighterId;
}
