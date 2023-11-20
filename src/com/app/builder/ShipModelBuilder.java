package com.app.builder;

import com.app.entity.ShipModel;
import com.app.exceptions.NoSuchColumnInResultSetException;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;

@UtilityClass
public class ShipModelBuilder {
    public ShipModel buildShipModel(ResultSet resultSet){
        try {
            return ShipModel.builder()
                    .shipModel(resultSet.getObject("ship_model",String.class))
                    .shipCapacity(resultSet.getObject("ship_capacity",Integer.class))
                    .shipSize(resultSet.getObject("ship_size",Integer.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }
}
