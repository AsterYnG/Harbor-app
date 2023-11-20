package com.app.dao;

import com.app.entity.Cargo;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildCargo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CargoDao implements Dao<Integer, Cargo> {

    private static final CargoDao INSTANCE = new CargoDao();

    public static CargoDao getInstance() {
        return INSTANCE;
    }

    private CargoDao(){

    }

    private static final String FIND_ALL = """
            SELECT * FROM cargo
                JOIN freighter f on f.freighter_id = cargo.freighter_id
                JOIN "Order" O on O.order_id = cargo.order_id
                JOIN customer c on c.customer_id = cargo.customer_id;
            """;

    @Override
    public List<Cargo> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<Cargo> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildCargo(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<Cargo> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Cargo update(Cargo entity) {
        return null;
    }

    @Override
    public Cargo save(Cargo entity) {
        return null;
    }



}
