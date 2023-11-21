package com.app.dao;

import com.app.entity.Registration;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildRegistration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class RegistrationDao implements Dao<Integer, Registration> {
    private static final RegistrationDao INSTANCE = new RegistrationDao();

    public static RegistrationDao getInstance() {
        return INSTANCE;
    }

    private RegistrationDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM registration;
    """;

    private final static String FIND_BY_ID = """
        SELECT * 
        FROM registration
        WHERE regId = ?
    """;

    private final static String DELETE = """
        DELETE FROM registration 
        WHERE regId IN ?
    """;

    @Override
    public List<Registration> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<Registration> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildRegistration(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<Registration> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            Registration result = buildRegistration(resultSet);
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
    public Registration update(Registration entity) {
        return null;
    }

    @Override
    public void save(Registration entity) {

    }
}