package com.app.dao;

import com.app.entity.Position;
import com.app.entity.Route;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildPosition;
import static com.app.util.EntityBuilder.buildRoute;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RouteDao implements Dao<Integer, Route>{


    private static final RouteDao INSTANCE = new RouteDao();

    public static RouteDao getInstance() {
        return INSTANCE;
    }

    private RouteDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM available_routes;
    """;
    private final static String FIND_BY_NAME = """
        SELECT *
        FROM available_routes
        WHERE destination_city = ?;
    """;

    private final static String FIND_COUNTRY_BY_NAME = """
        SELECT destination_country
        FROM available_routes
        WHERE destination_city = ?;
    """;


    @Override
    public List<Route> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<Route> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildRoute(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    public Optional<Route> findByName(String city) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_BY_NAME);
            preparedStatement.setString(1, city);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Route result = buildRoute(resultSet);
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<Route> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Route update(Route entity) {
        return null;
    }

    @Override
    public Route save(Route entity) {
        return entity;
    }
}
