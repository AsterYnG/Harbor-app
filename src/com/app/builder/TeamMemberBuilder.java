package com.app.builder;

import com.app.entity.TeamMember;
import com.app.exceptions.NoSuchColumnInResultSetException;
import lombok.experimental.UtilityClass;

import static com.app.builder.TeamBuilder.buildTeam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@UtilityClass
public class TeamMemberBuilder {

    public TeamMember buildTeamMember(ResultSet resultSet){
        try {
            return TeamMember.builder()
                    .memberId(resultSet.getObject("member_id",Integer.class))
                    .fullName(resultSet.getObject("full_name",String.class))
                    .position(resultSet.getObject("position",String.class))
                    .birthDate(resultSet.getObject("birth_date", LocalDate.class))
                    .citizenship(resultSet.getObject("citizenship", String.class))
                    .team(buildTeam(resultSet))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

}
