package com.app.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VoyageLog {
    private Integer logId;
    private Ship ship;
    private LocalDateTime shipmentDate;
}
