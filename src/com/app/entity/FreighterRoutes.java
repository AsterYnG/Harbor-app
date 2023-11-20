package com.app.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FreighterRoutes {
    private Freighter freighterId;
    private Route routeId;
    private Integer count;
}
