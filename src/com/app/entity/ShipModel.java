package com.app.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipModel {
    private String shipModel;
    private Integer shipCapacity;
    private Integer shipSize;
}
