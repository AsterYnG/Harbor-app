package com.app.dao;

import com.app.entity.Order;
import com.app.entity.TeamMember;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildTeamMember;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TeamMemberDao implements Dao<Integer, TeamMember> {
    private static final TeamMemberDao INSTANCE = new TeamMemberDao();

    public static TeamMemberDao getInstance() {
        return INSTANCE;
    }

    private TeamMemberDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM team_member
            JOIN public.team t on t.team_id = team_member.team_id;
    """;

    @Override
    public List<TeamMember> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<TeamMember> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildTeamMember(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<TeamMember> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public TeamMember update(TeamMember entity) {
        return null;
    }

    @Override
    public TeamMember save(TeamMember entity) {
        return null;
    }
}
