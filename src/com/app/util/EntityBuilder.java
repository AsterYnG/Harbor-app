package com.app.util;

import com.app.entity.*;
import com.app.exceptions.NoSuchColumnInResultSetException;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;


@UtilityClass
public class EntityBuilder {

    public Cargo buildCargo(ResultSet resultSet){
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

    public Customer buildCustomer(ResultSet resultSet){
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

    public Freighter buildFreighter(ResultSet resultSet){
        try {
            return Freighter.builder()
                    .freighterId(resultSet.getObject("freighter_id",Integer.class))
                    .tax(resultSet.getObject("tax",Integer.class))
                    .weightCost(resultSet.getObject("weight_cost",Integer.class))
                    .sizeCost(resultSet.getObject("size_cost",Integer.class))
                    .freighterName(resultSet.getObject("freighter_name",String.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

    public FreighterRoutes buildFreighterRoutes(ResultSet resultSet){
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

    public Order buildOrder(ResultSet resultSet){
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

    public Route buildRoute(ResultSet resultSet){
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

    public Ship buildShip(ResultSet resultSet){
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

    public ShipModel buildShipModel(ResultSet resultSet){
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

    public Team buildTeam(ResultSet resultSet){
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
    public TeamMember buildTeamMember(ResultSet resultSet){
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

    public VoyageLog buildVoyageLog(ResultSet resultSet){
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
