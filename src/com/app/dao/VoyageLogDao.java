package com.app.dao;


import com.app.entity.VoyageLog;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildVoyageLog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VoyageLogDao implements Dao<Integer, VoyageLog> {

    private static final VoyageLogDao INSTANCE = new VoyageLogDao();

    public static VoyageLogDao getInstance() {
        return INSTANCE;
    }

    private VoyageLogDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM voyage_log
            JOIN public.ship s on s.ship_id = voyage_log.ship_id;
    """;
    @Override
    public List<VoyageLog> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<VoyageLog> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildVoyageLog(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<VoyageLog> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public VoyageLog update(VoyageLog entity) {
        return null;
    }

    @Override
    public void save(VoyageLog entity) {

    }
}
