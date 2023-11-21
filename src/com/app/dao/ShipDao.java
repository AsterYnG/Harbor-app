package com.app.dao;


import com.app.entity.Ship;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildShip;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ShipDao implements Dao<Integer, Ship> {

    private static final ShipDao INSTANCE = new ShipDao();

    public static ShipDao getInstance() {
        return INSTANCE;
    }

    private ShipDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM ship
            JOIN freighter f on f.freighter_id = ship.freighter_id
            JOIN team t on t.team_id = ship.team_id
            JOIN ship_model sm on sm.ship_model = ship.ship_model;
    """;

    @Override
    public List<Ship> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<Ship> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildShip(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<Ship> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Ship update(Ship entity) {
        return null;
    }

    @Override
    public void save(Ship entity) {

    }
}
