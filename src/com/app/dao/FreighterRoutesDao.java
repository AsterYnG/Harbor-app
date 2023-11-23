package com.app.dao;


import com.app.entity.FreighterRoutes;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildFreighterRoutes;

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
            JOIN public.available_routes ar on ar.route_id = fr.route_id;
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
        return entity;
    }
}
