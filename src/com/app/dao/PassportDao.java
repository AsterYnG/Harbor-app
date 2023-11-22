package com.app.dao;

import com.app.entity.Passport;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildPassport;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PassportDao implements Dao<Integer, Passport> {
    private static final PassportDao INSTANCE = new PassportDao();

    public static PassportDao getInstance() {
        return INSTANCE;
    }

    private PassportDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM passport;
    """;

    private final static String FIND_BY_ID = """
        SELECT * 
        FROM passport
        WHERE passport_serial_number = ?;
    """;

    private final static String DELETE = """
        DELETE FROM passport 
        WHERE passport_serial_number = ?;
    """;

    @Override
    public List<Passport> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<Passport> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildPassport(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<Passport> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            Passport result = buildPassport(resultSet);
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
    public Passport update(Passport entity) {
        return null;
    }

    @Override
    public void save(Passport entity) {

    }
}