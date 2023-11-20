package com.app.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Route {
    private Integer routeId;
    private String destinationCountry;
    private String destinationCity;
    private Integer duration;
}
