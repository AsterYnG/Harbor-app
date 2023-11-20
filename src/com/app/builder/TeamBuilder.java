package com.app.builder;

import com.app.entity.Team;
import com.app.exceptions.NoSuchColumnInResultSetException;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;

@UtilityClass
public class TeamBuilder {
    public Team buildTeam(ResultSet resultSet){
        try {
            return Team.builder()
                    .teamId(resultSet.getObject("team_id",Integer.class))
                    .experience(resultSet.getObject("experience",Integer.class))
                    .description(resultSet.getObject("description",String.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }
}
