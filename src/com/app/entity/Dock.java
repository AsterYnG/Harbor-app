package com.app.entity;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public final class Dock {
    private Integer dockId;
    private Integer shipCapacity;
    private Freighter freighterName;
}