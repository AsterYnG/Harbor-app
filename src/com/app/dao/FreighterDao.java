package com.app.dao;

import com.app.entity.Freighter;
import com.app.exceptions.NoSuchColumnInResultSetException;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;
import lombok.Value;

import java.sql.ResultSet;
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
    public Freighter save(Freighter entity) {
        return null;
    }

    private Freighter buildFreighter(ResultSet resultSet) {
        try {
            return Freighter.builder()
                    .freighterId(resultSet.getInt("freighter_id"))
                    .tax(resultSet.getInt("tax"))
                    .freighterName(resultSet.getString("freighter_name"))
                    .fragileCost(resultSet.getInt("fragile_cost"))
                    .sizeCost(resultSet.getInt("size_cost"))
                    .weightCost(resultSet.getInt("weight_cost"))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }


}
