package com.app.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Ship {
    private Integer shipId;
    private Boolean inUse;
    private Team team;
    private ShipModel shipModel;
    private Freighter freighter;
}
