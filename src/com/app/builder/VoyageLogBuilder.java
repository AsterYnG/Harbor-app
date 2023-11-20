package com.app.builder;

import com.app.entity.VoyageLog;
import com.app.exceptions.NoSuchColumnInResultSetException;
import lombok.experimental.UtilityClass;

import static com.app.builder.ShipBuilder.buildShip;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@UtilityClass
public class VoyageLogBuilder {
    public VoyageLog buildVoyageLog(ResultSet resultSet){
        try {
            return VoyageLog.builder()
                    .logId(resultSet.getObject("log_id",Integer.class))
                    .ship(buildShip(resultSet))
                    .shipmentDate(resultSet.getObject("shipment_date", LocalDateTime.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }
}
