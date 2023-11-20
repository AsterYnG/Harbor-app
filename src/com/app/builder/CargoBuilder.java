package com.app.builder;

import com.app.entity.Cargo;
import com.app.exceptions.NoSuchColumnInResultSetException;
import lombok.experimental.UtilityClass;
import static com.app.builder.CustomerBuilder.buildCustomer;
import static com.app.builder.FreighterBuilder.buildFreighter;
import static com.app.builder.OrderBuilder.buildOrder;

import java.sql.ResultSet;
import java.sql.SQLException;

@UtilityClass
public class CargoBuilder {
    public Cargo buildCargo(ResultSet resultSet){
        try {
            return Cargo.builder()
                    .cargoId(resultSet.getObject("cargo_id",Integer.class))
                    .cargoWeight(resultSet.getObject("cargo_weight",Integer.class))
                    .isFragile(resultSet.getObject("is_fragile",Boolean.class))
                    .cargoSize(resultSet.getObject("cargo_size",Integer.class))
                    .customer(buildCustomer(resultSet))
                    .freighter(buildFreighter(resultSet))
                    .order(buildOrder(resultSet))
                    .destination(resultSet.getObject("destination",String.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }
}
