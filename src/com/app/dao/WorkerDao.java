package com.app.dao;

import com.app.entity.Worker;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildWorker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class WorkerDao implements Dao<Integer, Worker> {
    private static final WorkerDao INSTANCE = new WorkerDao();

    public static WorkerDao getInstance() {
        return INSTANCE;
    }

    private WorkerDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM worker;
    """;

    private final static String FIND_BY_ID = """
        SELECT * 
        FROM worker
        WHERE worker_id = ?;
    """;

    private final static String DELETE = """
        DELETE FROM worker
        WHERE worker_id = ?;
    """;

    @Override
    public List<Worker> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<Worker> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildWorker(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<Worker> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            Worker result = buildWorker(resultSet);
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
    public Worker update(Worker entity) {
        return null;
    }

    @Override
    public void save(Worker entity) {

    }
}