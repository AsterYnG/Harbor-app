package com.app.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class FreighterRoutes {
    private Freighter freighter;
    private Route route;
    private Integer count;
}
