package com.app.dao;

import com.app.entity.StoreHouse;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildStoreHouse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class StoreHouseDao implements Dao<Integer, StoreHouse> {
    private static final StoreHouseDao INSTANCE = new StoreHouseDao();

    public static StoreHouseDao getInstance() {
        return INSTANCE;
    }

    private StoreHouseDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM store_house;
    """;

    private final static String FIND_BY_ID = """
        SELECT * 
        FROM store_house
        WHERE storeId = ?
    """;

    private final static String DELETE = """
        DELETE FROM store_house
        WHERE storeId IN ?
    """;

    @Override
    public List<StoreHouse> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<StoreHouse> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildStoreHouse(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<StoreHouse> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            StoreHouse result = buildStoreHouse(resultSet);
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public StoreHouse update(StoreHouse entity) {
        return null;
    }

    @Override
    public StoreHouse save(StoreHouse entity) {
        return null;
    }
}