package com.app.dao;

import com.app.entity.Dock;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildDock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class DockDao implements Dao<Integer, Dock> {
    private static final DockDao INSTANCE = new DockDao();

    public static DockDao getInstance() {
        return INSTANCE;
    }

    private DockDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM dock;
    """;

    private final static String FIND_BY_ID = """
        SELECT * 
        FROM dock
        WHERE dock_id = ?;
    """;

    private final static String DELETE = """
        DELETE FROM dock
        WHERE dock_id = ?;
    """;

    private final static String FIND_LEAST_DOCK  = """
        SELECT * FROM dock_with_least_workers();
    """;

    public Dock findLeastWorkersDock(){
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_LEAST_DOCK);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildDock(resultSet);
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }
    @Override
    public List<Dock> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<Dock> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildDock(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<Dock> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Dock result = buildDock(resultSet);
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
    public Dock update(Dock entity) {
        return null;
    }

    @Override
    public void save(Dock entity) {
    }
}