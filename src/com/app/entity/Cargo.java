package com.app.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Cargo {
    private Integer cargoId;
    private Integer cargoWeight;
    private Boolean isFragile;
    private Integer cargoSize;
    private Customer customer;
    private Freighter freighter;
    private Order order;
    private String destination;
}
