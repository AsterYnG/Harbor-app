package com.app.entity;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public final class StoreHouse {
    private Integer storeId;
    private Integer capacity;
    private Dock dockId;
}