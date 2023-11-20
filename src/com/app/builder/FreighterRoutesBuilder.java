package com.app.builder;

import com.app.entity.FreighterRoutes;
import com.app.exceptions.NoSuchColumnInResultSetException;
import lombok.experimental.UtilityClass;

import static com.app.builder.RouteBuilder.buildRoute;
import static com.app.builder.FreighterBuilder.buildFreighter;

import java.sql.ResultSet;
import java.sql.SQLException;

@UtilityClass
public class FreighterRoutesBuilder {
    public FreighterRoutes buildFreighterRoutes(ResultSet resultSet){
        try {
            return FreighterRoutes.builder()
                    .route(buildRoute(resultSet))
                    .freighter(buildFreighter(resultSet))
                    .count(resultSet.getObject("count",Integer.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }
}
