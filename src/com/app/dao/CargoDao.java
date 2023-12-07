package com.app.dao;

import com.app.entity.Cargo;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildCargo;

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

    private final static String SAVE = """
        INSERT INTO cargo(cargo_weight, is_fragile, cargo_size, customer_id, freighter_id, destination)
        VALUES (?,?,?,?,?,?);
    """;

    private final static String DELETE_BY_ORDER_ID = """
        DELETE FROM cargo WHERE order_id = ?;
    """;

    private final static String UPDATE = """
        UPDATE cargo
        SET cargo_weight = ?,
            is_fragile = ?,
            cargo_size = ?,
            customer_id = ?,
            freighter_id = ?,
            destination = ?
        WHERE order_id = ?;
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
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(DELETE_BY_ORDER_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Cargo update(Cargo entity) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setInt(1, entity.getCargoWeight());
            preparedStatement.setBoolean(2, entity.getIsFragile());
            preparedStatement.setInt(3, entity.getCargoSize());
            preparedStatement.setInt(4, entity.getCustomer().getCustomerId());
            preparedStatement.setInt(5, entity.getFreighter().getFreighterId());
            preparedStatement.setString(6, entity.getDestination());
            preparedStatement.setInt(7, entity.getOrder().getOrderId());
            preparedStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Cargo save(Cargo entity) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(SAVE);
            preparedStatement.setInt(1, entity.getCargoWeight());
            preparedStatement.setBoolean(2, entity.getIsFragile());
            preparedStatement.setInt(3, entity.getCargoSize());
            preparedStatement.setInt(4, entity.getCustomer().getCustomerId());
            preparedStatement.setInt(5, entity.getFreighter().getFreighterId());
            preparedStatement.setString(6, entity.getDestination());
            preparedStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }


}
