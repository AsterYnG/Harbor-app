package com.app.dao;

import com.app.entity.Education;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildEducation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class EducationDao implements Dao<Integer, Education> {
    private static final EducationDao INSTANCE = new EducationDao();

    public static EducationDao getInstance() {
        return INSTANCE;
    }

    private EducationDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM education;
    """;

    private final static String FIND_BY_ID = """
        SELECT * 
        FROM education 
        WHERE educationSerialNumber = ?
    """;

    private final static String DELETE = """
        DELETE FROM education 
        WHERE educationSerialNumber IN ?
    """;

    @Override
    public List<Education> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<Education> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildEducation(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<Education> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            Education result = buildEducation(resultSet);
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
    public Education update(Education entity) {
        return null;
    }

    @Override
    public Education save(Education entity) {
        return null;
    }
}