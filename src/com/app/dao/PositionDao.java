package com.app.dao;

import com.app.entity.Position;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildPosition;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PositionDao implements Dao<Integer, Position> {
    private static final PositionDao INSTANCE = new PositionDao();

    public static PositionDao getInstance() {
        return INSTANCE;
    }

    private PositionDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM position;
    """;

    private final static String FIND_BY_NAME = """
        SELECT * 
        FROM position
        WHERE position = ?
    """;

    private final static String DELETE = """
        DELETE FROM position 
        WHERE position IN ?
    """;

    @Override
    public List<Position> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<Position> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildPosition(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<Position> findById(Integer id) {
        return Optional.empty();
    }

    public Optional<Position> findByName(String pos) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_BY_NAME);
            preparedStatement.setString(1, pos);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            Position result = buildPosition(resultSet);
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Position update(Position entity) {
        return null;
    }

    @Override
    public void save(Position entity) {

    }
}