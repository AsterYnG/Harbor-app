package com.app.dao;

import com.app.entity.MedicalCard;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildMedicalCard;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MedicalCardDao implements Dao<Integer, MedicalCard> {
    private static final MedicalCardDao INSTANCE = new MedicalCardDao();

    public static MedicalCardDao getInstance() {
        return INSTANCE;
    }

    private MedicalCardDao() {
    }

    private final static String FIND_ALL = """
        SELECT * FROM medical_card;
    """;

    private final static String FIND_BY_ID = """
        SELECT * 
        FROM medical_card
        WHERE medSerialNumber = ?
    """;

    private final static String DELETE = """
        DELETE FROM medical_card 
        WHERE medSerialNumber IN ?
    """;

    @Override
    public List<MedicalCard> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<MedicalCard> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildMedicalCard(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<MedicalCard> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            MedicalCard result = buildMedicalCard(resultSet);
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
    public MedicalCard update(MedicalCard entity) {
        return null;
    }

    @Override
    public void save(MedicalCard entity) {

    }
}