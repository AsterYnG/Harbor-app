package com.app.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Order {
    private LocalDateTime date;
    private Integer orderId;
    private String status;
}
