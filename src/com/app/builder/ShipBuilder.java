package com.app.builder;

import com.app.entity.Ship;
import com.app.exceptions.NoSuchColumnInResultSetException;
import lombok.experimental.UtilityClass;

import static com.app.builder.TeamBuilder.buildTeam;
import static com.app.builder.ShipModelBuilder.buildShipModel;
import static com.app.builder.FreighterBuilder.buildFreighter;

import java.sql.ResultSet;
import java.sql.SQLException;

@UtilityClass
public class ShipBuilder {
    public Ship buildShip(ResultSet resultSet){
        try {
            return Ship.builder()
                    .shipId(resultSet.getObject("ship_id",Integer.class))
                    .inUse(resultSet.getObject("in_use", Boolean.class))
                    .team(buildTeam(resultSet))
                    .shipModel(buildShipModel(resultSet))
                    .freighter(buildFreighter(resultSet))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }
}
