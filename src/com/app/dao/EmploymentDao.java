package com.app.dao;

import com.app.entity.Employment;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildEmployment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class EmploymentDao implements Dao<Integer, Employment> {
    private static final EmploymentDao INSTANCE = new EmploymentDao();

    public static EmploymentDao getInstance() {
        return INSTANCE;
    }

    private EmploymentDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM employment;
    """;

    private final static String FIND_BY_ID = """
        SELECT * 
        FROM employment 
        WHERE employment_serial_number = ?;
    """;

    private final static String DELETE = """
        DELETE FROM employment 
        WHERE employmentSerialNumber IN ?
    """;

    @Override
    public List<Employment> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<Employment> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildEmployment(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<Employment> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            Employment result = buildEmployment(resultSet);
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
    public Employment update(Employment entity) {
        return null;
    }

    @Override
    public void save(Employment entity) {

    }
}