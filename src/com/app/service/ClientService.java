package com.app.service;

import com.app.dao.*;
import com.app.dto.CreateCargoDto;
import com.app.entity.*;
import com.app.util.Mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClientService {
    private static final ClientService INSTANCE = new ClientService();

    private final OrderDao orderDao = OrderDao.getInstance();
    private final CargoDao cargoDao = CargoDao.getInstance();
    private final RouteDao routeDao = RouteDao.getInstance();
    private final FreighterDao freighterDao = FreighterDao.getInstance();

    private final FreighterRoutesDao freighterRoutesDao = FreighterRoutesDao.getInstance();

    private ClientService() {
    }

    public static ClientService getInstance(){
        return INSTANCE;
    }
    public final List<Optional<Order>> getOrderHistory(Customer customer) {
        List<Optional<Order>> clientOrders = orderDao.customerOrders(customer.getCustomerId());
        return clientOrders;
    }

    public List<Optional<Order>> getCurrentOrders(Customer customer) {
        List<Optional<Order>> clientCurOrders = orderDao.currentCustomerOrders(customer.getCustomerId());
        return clientCurOrders;
    }

    public List<FreighterRoutes> getAvailableRoutes() {
        List<FreighterRoutes> cities = freighterRoutesDao.findAll();
        return cities;
    }
    public List<Freighter> getAvailableFreighters(String city) {
        List<Freighter> availableFreighters = freighterDao.getAvailableFreightersByDirection(city);
        return availableFreighters;
    }

    public Optional<Route> findByRouteName(String city){
        return routeDao.findByName(city);
    }

    public Freighter getFreighterByName (String name) {
        return freighterDao.findByName(name);
    }

    public double calculateShippingCost(CreateCargoDto cargo, Freighter freighter) {
        double cost = cargo.getCargoWeight() * freighter.getWeightCost() +
                      (cargo.getIsFragile() ? freighter.getFragileCost() : 0) +
                      cargo.getCargoSize() * freighter.getSizeCost() +
                      freighter.getTax();
        return cost;
    }

    public void createOrder(CreateCargoDto cargoDto, Freighter freighter, Customer customer, String destination) {
        Cargo curCargo = Cargo.builder()
                .cargoSize(cargoDto.getCargoSize())
                .cargoWeight(cargoDto.getCargoWeight())
                .isFragile(cargoDto.getIsFragile())
                .destination(destination)
                .freighter(freighter)
                .customer(customer)
                .build();
        cargoDao.save(curCargo);
    }

    public String editClientProfile(String clientId, String newProfileData) {
        // добавить логику на редактирование профиля клиента
        return "Профиль клиента отредактирован: " + newProfileData;
    }
}