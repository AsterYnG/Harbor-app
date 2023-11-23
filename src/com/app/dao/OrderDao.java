package com.app.dao;


import com.app.entity.Order;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildOrder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDao implements Dao<Integer, Order> {

    private static final OrderDao INSTANCE = new OrderDao();

    public static OrderDao getInstance() {
        return INSTANCE;
    }

    private OrderDao() {
    }

    private final static String FIND_ALL = """
                SELECT * FROM "Order";
            """;

    private final static String CUSTOMER_ORDER = """
            SELECT * FROM "Order"
            JOIN public.cargo c on "Order".order_id = c.order_id
            JOIN public.customer c2 on c2.customer_id = c.customer_id
            WHERE c.customer_id = ?;
            """;

    @Override
    public List<Order> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<Order> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildOrder(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    public List<Optional<Order>> customerOrders(Integer id) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(CUSTOMER_ORDER);
            preparedStatement.setInt(1, id);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<Optional<Order>> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(Optional.of(buildOrder(resultSet)));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Order update(Order entity) {
        return null;
    }

    @Override
    public Order save(Order entity) {
        return entity;
    }
}
