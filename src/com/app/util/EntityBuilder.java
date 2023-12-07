package com.app.util;

import com.app.dto.CreateCargoDto;
import com.app.entity.*;
import com.app.exceptions.NoSuchColumnInResultSetException;
import lombok.experimental.UtilityClass;
import org.postgresql.jdbc.PgResultSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;


@UtilityClass
public class EntityBuilder {

    public static Dock buildDock(ResultSet resultSet){
        try {
            return Dock.builder()
                    .dockId(resultSet.getObject("dock_id",Integer.class))
                    .shipCapacity(resultSet.getObject("ship_capacity",Integer.class))
                    .freighterName(buildFreighter(resultSet))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static StoreHouse buildStoreHouse(ResultSet resultSet){
        try {
            return StoreHouse.builder()
                    .storeId(resultSet.getObject("store_id",Integer.class))
                    .capacity(resultSet.getObject("capacity",Integer.class))
                    .dockId(buildDock(resultSet))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static Education buildEducation(ResultSet resultSet){
        try {
            return Education.builder()
                    .educationSerialNumber(resultSet.getObject("education_serial_number",String.class))
                    .grade(resultSet.getObject("grade",String.class))
                    .establishment(resultSet.getObject("establishment",String.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static Employment buildEmployment(ResultSet resultSet){
        try {
            return Employment.builder()
                    .employmentSerialNumber(resultSet.getObject("employment_serial_number",String.class))
                    .previousJob(resultSet.getObject("previous_job",String.class))
                    .experience(resultSet.getObject("experience",Integer.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static MedicalCard buildMedicalCard(ResultSet resultSet){
        try {
            return MedicalCard.builder()
                    .medSerialNumber(resultSet.getObject("med_serial_number",String.class))
                    .hivStatus(resultSet.getObject("hiv_status",Boolean.class))
                    .illness(resultSet.getObject("illness",String.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static Registration buildRegistration(ResultSet resultSet){
        try {
            return Registration.builder()
                    .regId(resultSet.getObject("reg_id",Integer.class))
                    .house(resultSet.getObject("house",String.class))
                    .flat(resultSet.getObject("flat",Integer.class))
                    .region(resultSet.getObject("region",String.class))
                    .city(resultSet.getObject("city",String.class))
                    .street(resultSet.getObject("street",String.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static Passport buildPassport(ResultSet resultSet){
        try {
            return Passport.builder()
                    .passportSerialNumber(resultSet.getObject("passport_serial_number",String.class))
                    .fullName(resultSet.getObject("full_name",String.class))
                    .birthDate(resultSet.getObject("birth_date",LocalDate.class))
                    .sex(resultSet.getObject("sex",String.class))
                    .citizenship(resultSet.getObject("citizenship",String.class))
                    .regId(buildRegistration(resultSet))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static Position buildPosition(ResultSet resultSet){
        try {
            return Position.builder()
                    .position(resultSet.getObject("position",String.class))
                    .salary(resultSet.getObject("salary",Integer.class))
                    .description(resultSet.getObject("description",String.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static Worker buildWorker(ResultSet resultSet){
        try {
            return Worker.builder()
                    .workerId(resultSet.getObject("worker_id",Integer.class))
                    .hiringDate(resultSet.getObject("hiring_date",LocalDate.class))
                    .position(buildPosition(resultSet))
                    .medSerialNumber(buildMedicalCard(resultSet))
                    .passportSerialNumber(buildPassport(resultSet))
                    .educationSerialNumber(buildEducation(resultSet))
                    .employmentSerialNumber(buildEmployment(resultSet))
                    .dockId(buildDock(resultSet))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static Cargo buildCargo(ResultSet resultSet){
        try {
            return Cargo.builder()
                    .cargoId(resultSet.getObject("cargo_id",Integer.class))
                    .cargoWeight(resultSet.getObject("cargo_weight",Integer.class))
                    .isFragile(resultSet.getObject("is_fragile",Boolean.class))
                    .cargoSize(resultSet.getObject("cargo_size",Integer.class))
                    .customer(buildCustomer(resultSet))
                    .freighter(buildFreighter(resultSet))
                    .order(buildOrder(resultSet))
                    .destination(resultSet.getObject("destination",String.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static CreateCargoDto buildCargoDto(ResultSet resultSet){
        try {
            return CreateCargoDto.builder()
                    .cargoWeight(resultSet.getObject("cargo_weight",Integer.class))
                    .isFragile(resultSet.getObject("is_fragile",Boolean.class))
                    .cargoSize(resultSet.getObject("cargo_size",Integer.class))
                    .destination(resultSet.getObject("destination",String.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static Customer buildCustomer(ResultSet resultSet){
        try {
            return Customer.builder()
                    .customerId(resultSet.getObject("customer_id",Integer.class))
                    .fullName(resultSet.getObject("full_name",String.class))
                    .password(resultSet.getObject("password",String.class))
                    .login(resultSet.getObject("login",String.class))
                    .email(resultSet.getObject("email",String.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static Freighter buildFreighter(ResultSet resultSet){
        try {
            return Freighter.builder()
                    .freighterId(resultSet.getObject("freighter_id",Integer.class))
                    .tax(resultSet.getObject("tax",Integer.class))
                    .weightCost(resultSet.getObject("weight_cost",Integer.class))
                    .sizeCost(resultSet.getObject("size_cost",Integer.class))
                    .freighterName(resultSet.getObject("freighter_name",String.class))
                    .fragileCost(resultSet.getObject("fragile_cost",Integer.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static FreighterRoutes buildFreighterRoutes(ResultSet resultSet){
        try {
            return FreighterRoutes.builder()
                    .route(buildRoute(resultSet))
                    .freighter(buildFreighter(resultSet))
                    .count(resultSet.getObject("count",Integer.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static Order buildOrder(ResultSet resultSet){
        try {
            return Order.builder()
                    .orderId(resultSet.getObject("order_id",Integer.class))
                    .status(resultSet.getObject("status",String.class))
                    .date(resultSet.getObject("date", LocalDateTime.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static Route buildRoute(ResultSet resultSet){
        try {
            return Route.builder()
                    .routeId(resultSet.getObject("route_id",Integer.class))
                    .destinationCountry(resultSet.getObject("destination_country",String.class))
                    .destinationCity(resultSet.getObject("destination_city",String.class))
                    .duration(resultSet.getObject("duration",Integer.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static Ship buildShip(ResultSet resultSet){
        try {
            return Ship.builder()
                    .shipId(resultSet.getObject("ship_id",Integer.class))
                    .inUse(resultSet.getObject("in_use", Boolean.class))
                    .team(buildTeam(resultSet))
                    .shipModel(buildShipModel(resultSet))
                    .freighter(buildFreighter(resultSet))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static ShipModel buildShipModel(ResultSet resultSet){
        try {
            return ShipModel.builder()
                    .shipModel(resultSet.getObject("ship_model",String.class))
                    .shipCapacity(resultSet.getObject("ship_capacity",Integer.class))
                    .shipSize(resultSet.getObject("ship_size",Integer.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static Team buildTeam(ResultSet resultSet){
        try {
            return Team.builder()
                    .teamId(resultSet.getObject("team_id",Integer.class))
                    .experience(resultSet.getObject("experience",Integer.class))
                    .description(resultSet.getObject("description",String.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }
    public static TeamMember buildTeamMember(ResultSet resultSet){
        try {
            return TeamMember.builder()
                    .memberId(resultSet.getObject("member_id",Integer.class))
                    .fullName(resultSet.getObject("full_name",String.class))
                    .position(resultSet.getObject("position",String.class))
                    .birthDate(resultSet.getObject("birth_date", LocalDate.class))
                    .citizenship(resultSet.getObject("citizenship", String.class))
                    .team(buildTeam(resultSet))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public static VoyageLog buildVoyageLog(ResultSet resultSet){
        try {
            return VoyageLog.builder()
                    .logId(resultSet.getObject("log_id",Integer.class))
                    .ship(buildShip(resultSet))
                    .shipmentDate(resultSet.getObject("shipment_date", LocalDateTime.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }
}
