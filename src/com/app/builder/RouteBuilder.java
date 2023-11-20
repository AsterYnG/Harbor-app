package com.app.builder;

import com.app.entity.Route;
import com.app.exceptions.NoSuchColumnInResultSetException;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;

@UtilityClass
public class RouteBuilder {
    public Route buildRoute(ResultSet resultSet){
        try {
            return Route.builder()
                    .routeId(resultSet.getObject("route_id",Integer.class))
                    .destinationCountry(resultSet.getObject("destination_country",String.class))
                    .destinationCity(resultSet.getObject("destination_city",String.class))
                    .duration(resultSet.getObject("duration",Integer.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }
}
