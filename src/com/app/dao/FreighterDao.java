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
    private static final String SAVE = """
            INSERT INTO freighter(tax, weight_cost, size_cost, fragile_cost, freighter_name)
            VALUES (?,?,?,?,?);
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
    public Freighter save(Freighter entity) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(SAVE);
            preparedStatement.setInt(1,entity.getTax());
            preparedStatement.setInt(2,entity.getWeightCost());
            preparedStatement.setInt(3,entity.getSizeCost());
            preparedStatement.setInt(4,entity.getFragileCost());
            preparedStatement.setString(5,entity.getFreighterName());
            preparedStatement.executeUpdate();

            return entity;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

}
