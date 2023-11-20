package com.app.dao;

import com.app.entity.Customer;
import com.app.entity.Order;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.builder.CustomerBuilder.buildCustomer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.builder.OrderBuilder.buildOrder;

public class CustomerDao implements Dao<Integer, Customer> {
    private static final CustomerDao INSTANCE = new CustomerDao();

    public static CustomerDao getInstance() {
        return INSTANCE;
    }

    private CustomerDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM customer;
    """;

    @Override
    public List<Customer> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<Customer> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildCustomer(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Customer update(Customer entity) {
        return null;
    }

    @Override
    public Customer save(Customer entity) {
        return null;
    }
}
