package com.app.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cargo {
    private Integer cargoId;
    private Integer cargoWeight;
    private Boolean isFragile;
    private Integer cargoSize;
    private Customer customerId;
    private Freighter freighterId;
    private Order orderId;
    private String destination;
}
