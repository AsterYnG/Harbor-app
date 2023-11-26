package com.app.dao;


import com.app.entity.FreighterRoutes;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildFreighterRoutes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FreighterRoutesDao implements Dao<Map<String,Integer>, FreighterRoutes> {

    private static final FreighterRoutesDao INSTANCE = new FreighterRoutesDao();

    public static FreighterRoutesDao getInstance() {
        return INSTANCE;
    }

    private FreighterRoutesDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM available_routes
        JOIN public.freighter_routes fr on available_routes.route_id = fr.route_id
        JOIN public.freighter f on f.freighter_id = fr.freighter_id;
    """;

    private final static String FIND_CITIES = """
        SELECT destination_city FROM available_routes;
    """;
    private final static String SAVE = """
        INSERT INTO freighter_routes(freighter_id, route_id)
        VALUES (?,?);
    """;


    @Override
    public List<FreighterRoutes> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<FreighterRoutes> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildFreighterRoutes(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }
    public List<String> findAvailableCities() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_CITIES);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<String> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(String.valueOf(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }
    @Override
    public Optional<FreighterRoutes> findById(Map<String, Integer> id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Map<String, Integer> id) {
        return false;
    }

    @Override
    public FreighterRoutes update(FreighterRoutes entity) {
        return null;
    }

    @Override
    public FreighterRoutes save(FreighterRoutes entity) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(SAVE, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,entity.getFreighter().getFreighterId());
            preparedStatement.setInt(2, entity.getRoute().getRouteId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            return entity;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }
}
