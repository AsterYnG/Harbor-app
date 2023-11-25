package com.app.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
public final class Order {
    private LocalDateTime date;
    private Integer orderId;
    private String status;

    @Override
    public String toString() {
        return "Дата заказа: " + date.toLocalDate() + ", " +
               "Время заказа: " + date.toLocalTime() + ", " +
               "Статус заказа: " + status;
    }
}
