package com.app.dao;

import com.app.entity.Worker;
import com.app.exceptions.UnableToTakeConnectionException;
import com.app.util.ConnectionManager;
import lombok.Cleanup;

import static com.app.util.EntityBuilder.buildWorker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class WorkerDao implements Dao<Integer, Worker> {
    private static final WorkerDao INSTANCE = new WorkerDao();

    public static WorkerDao getInstance() {
        return INSTANCE;
    }

    private WorkerDao() {
    }

    private final static String FIND_ALL = """
        SELECT *
        FROM worker
            LEFT JOIN dock d on d.dock_id = worker.dock_id
            JOIN position p on p.position = worker.position
            LEFT JOIN medical_card mc on mc.med_serial_number = worker.med_serial_number
            LEFT JOIN passport p2 on p2.passport_serial_number = worker.passport_serial_number
            LEFT JOIN education e on e.education_serial_number = worker.education_serial_number
            LEFT JOIN employment e2 on e2.employment_serial_number = worker.employment_serial_number
            LEFT JOIN registration r on r.reg_id = p2.reg_id
            LEFT JOIN freighter f on f.freighter_id = d.freighter_id
            LEFT JOIN store_house sh on d.dock_id = sh.dock_id
            LEFT JOIN cargo c on d.freighter_id = c.freighter_id
            LEFT JOIN "Order" O on O.order_id = c.order_id
            LEFT JOIN customer c2 on c2.customer_id = c.customer_id
            LEFT JOIN ship s on f.freighter_id = s.freighter_id
            LEFT JOIN team t on t.team_id = s.team_id
            LEFT JOIN team_member tm on c2.full_name = tm.full_name
            LEFT JOIN ship_model sm on sm.ship_model = s.ship_model
            LEFT JOIN freighter_routes fr on d.freighter_id = fr.freighter_id
            LEFT JOIN voyage_log vl on s.ship_id = vl.ship_id
            LEFT JOIN available_routes ar on ar.route_id = fr.route_id;
    """;

    private final static String FIND_BY_ID = """
        SELECT *
        FROM worker
        WHERE worker_id = ?;
    """;

    private final static String DELETE = """
        DELETE FROM worker
        WHERE worker_id = ?;
    """;

    private final static String DELETE_BY_SERIAL = """
        DELETE FROM worker
        WHERE passport_serial_number = ?;
    """;

    private final static String SAVE = """
        INSERT INTO worker(hiring_date, position, med_serial_number, passport_serial_number, education_serial_number, employment_serial_number, dock_id)
        VALUES (?,?,?,?,?,?,?);
    """;
    private final static String SAVE2 = """
        INSERT INTO worker(hiring_date, position, med_serial_number, passport_serial_number, education_serial_number, employment_serial_number)
        VALUES (?,?,?,?,?,?);
    """;

    @Override
    public List<Worker> findAll() {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_ALL);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            List<Worker> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildWorker(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Optional<Worker> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            @Cleanup var resultSet = preparedStatement.executeQuery();
            Worker result = buildWorker(resultSet);
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }


    public boolean delete(String passport) {
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(DELETE_BY_SERIAL);
            preparedStatement.setString(1, passport);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }

    @Override
    public Worker update(Worker entity) {
        return null;
    }

    @Override
    public Worker save(Worker entity) {
        try (var connection = ConnectionManager.get()) {

            if(entity.getDockId() != null){
            @Cleanup var preparedStatement = connection.prepareStatement(SAVE);
            preparedStatement.setObject(1, entity.getHiringDate());
            preparedStatement.setString(2, entity.getPosition().getPosition());
            preparedStatement.setString(3, entity.getMedSerialNumber().getMedSerialNumber());
            preparedStatement.setString(4, entity.getPassportSerialNumber().getPassportSerialNumber());
            preparedStatement.setString(5, entity.getEducationSerialNumber().getEducationSerialNumber());
            preparedStatement.setString(6, entity.getEmploymentSerialNumber().getEmploymentSerialNumber());
            preparedStatement.setInt(7, entity.getDockId().getDockId());

            preparedStatement.executeUpdate();
            return entity;
            }
            else {
                @Cleanup var preparedStatement = connection.prepareStatement(SAVE2);
                preparedStatement.setObject(1, entity.getHiringDate());
                preparedStatement.setString(2, entity.getPosition().getPosition());
                preparedStatement.setString(3, entity.getMedSerialNumber().getMedSerialNumber());
                preparedStatement.setString(4, entity.getPassportSerialNumber().getPassportSerialNumber());
                preparedStatement.setString(5, entity.getEducationSerialNumber().getEducationSerialNumber());
                preparedStatement.setString(6, entity.getEmploymentSerialNumber().getEmploymentSerialNumber());

                preparedStatement.executeUpdate();
                return entity;
            }

        } catch (SQLException e) {
            throw new UnableToTakeConnectionException(e);
        }
    }
}