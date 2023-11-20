package com.app.builder;

import com.app.entity.Order;
import com.app.exceptions.NoSuchColumnInResultSetException;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@UtilityClass
public class OrderBuilder {
    public Order buildOrder(ResultSet resultSet){
        try {
            return Order.builder()
                    .orderId(resultSet.getObject("order_id",Integer.class))
                    .status(resultSet.getObject("status",String.class))
                    .date(resultSet.getObject("date", LocalDateTime.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }
}
