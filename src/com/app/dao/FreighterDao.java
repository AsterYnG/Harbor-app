package com.app.dao;

import com.app.entity.Freighter;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildFreighter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class FreighterDao implements Dao<Integer, Freighter> {

    private static final FreighterDao INSTANCE = new FreighterDao();

    public static FreighterDao getInstance() {
        return INSTANCE;
    }

    private FreighterDao() {
    }

    private static final String FIND_ALL = """
            SELECT * FROM freighter;
            """;

    @Override
    public List<Freighter> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<Freighter> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildFreighter(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<Freighter> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Freighter update(Freighter entity) {
        return null;
    }

    @Override
    public void save(Freighter entity) {

    }

}
