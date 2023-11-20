package com.app.dao;

import com.app.entity.ShipModel;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildShipModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ShipModelDao  implements Dao<String, ShipModel>{

    private static final ShipModelDao INSTANCE = new ShipModelDao();

    public static ShipModelDao getInstance() {
        return INSTANCE;
    }

    private ShipModelDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM ship_model;
    """;


    @Override
    public List<ShipModel> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<ShipModel> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildShipModel(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<ShipModel> findById(String id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public ShipModel update(ShipModel entity) {
        return null;
    }

    @Override
    public ShipModel save(ShipModel entity) {
        return null;
    }
}
