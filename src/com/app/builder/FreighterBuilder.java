package com.app.builder;

import com.app.entity.Freighter;
import com.app.exceptions.NoSuchColumnInResultSetException;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;

@UtilityClass
public class FreighterBuilder {

    public Freighter buildFreighter(ResultSet resultSet){
        try {
            return Freighter.builder()
                    .freighterId(resultSet.getObject("freighter_id",Integer.class))
                    .tax(resultSet.getObject("tax",Integer.class))
                    .weightCost(resultSet.getObject("weight_cost",Integer.class))
                    .sizeCost(resultSet.getObject("size_cost",Integer.class))
                    .freighterName(resultSet.getObject("freighter_name",String.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }
}
