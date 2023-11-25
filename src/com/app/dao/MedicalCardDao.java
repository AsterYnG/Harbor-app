package com.app.dao;

import com.app.entity.Employment;
import com.app.entity.MedicalCard;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildEmployment;
import static com.app.util.EntityBuilder.buildMedicalCard;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MedicalCardDao implements Dao<String, MedicalCard> {
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
        WHERE med_serial_number = ?;
    """;

    private final static String SAVE = """
        INSERT INTO medical_card(hiv_status, illness, med_serial_number)
        VALUES (?,?,?);
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
    public Optional<MedicalCard> findById(String id) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setString(1, id);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            MedicalCard result = null;
            if (resultSet.next()){
                result = buildMedicalCard(resultSet);
            }
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public MedicalCard update(MedicalCard entity) {
        return null;
    }

    @Override
    public MedicalCard save(MedicalCard entity) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(SAVE, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setBoolean(1, entity.getHivStatus());
            preparedStatement.setString(2, entity.getIllness());
            preparedStatement.setString(3, entity.getMedSerialNumber());
            preparedStatement.executeUpdate();

            return entity;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }
}