package com.app.dao;

import com.app.entity.Customer;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import com.app.util.EntityBuilder;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildCustomer;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
    private final static String SAVE = """
        INSERT INTO customer(full_name, email, password,login)\s
        VALUES (?,?,?,?);
    """;
    private final static String FIND_BY_LOGIN = """
        SELECT * FROM customer
        WHERE login = ?
            LIMIT 1;
    """;

    private final static String FIND_BY_EMAIL = """
        SELECT * FROM customer
        WHERE email = ?
            LIMIT 1;
    """;


    public Optional<Customer> findByEmail(String email){
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_BY_EMAIL);
            preparedStatement.setString(1,email);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            Customer result = null;
            if (resultSet.next()){
                result = buildCustomer(resultSet);
            }
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Customer> findByLogin(String login){
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_BY_LOGIN);
            preparedStatement.setString(1,login);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            Customer result = null;
            if (resultSet.next()){
                result = buildCustomer(resultSet);
            }
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
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
            throw new RuntimeException(e);
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
    public void save(Customer entity) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getFullName());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getPassword());
            preparedStatement.setString(4, entity.getLogin());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
