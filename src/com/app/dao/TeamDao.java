package com.app.dao;


import com.app.entity.Team;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildTeam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TeamDao  implements Dao<Integer, Team>{

    private static final TeamDao INSTANCE = new TeamDao();

    public static TeamDao getInstance() {
        return INSTANCE;
    }

    private TeamDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM team;
    """;


    @Override
    public List<Team> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<Team> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildTeam(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<Team> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Team update(Team entity) {
        return null;
    }

    @Override
    public Team save(Team entity) {
        return entity;
    }
}
