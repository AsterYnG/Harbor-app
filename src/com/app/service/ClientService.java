package com.app.service;

import com.app.dao.*;
import com.app.dto.CreateCargoDto;
import com.app.dto.CreatePassportDto;
import com.app.dto.ShowCustomerDto;
import com.app.entity.*;
import com.app.exceptions.ValidationException;
import com.app.util.Mapper;
import com.app.validator.PassportValidator;
import com.app.validator.PasswordValidator;

import java.time.LocalDateTime;
import java.util.*;

public class ClientService {
    private static final ClientService INSTANCE = new ClientService();

    private final OrderDao orderDao = OrderDao.getInstance();
    private final CargoDao cargoDao = CargoDao.getInstance();
    private final RouteDao routeDao = RouteDao.getInstance();
    private final FreighterDao freighterDao = FreighterDao.getInstance();
    private final CustomerDao customerDao = CustomerDao.getInstance();
    private final FreighterRoutesDao freighterRoutesDao = FreighterRoutesDao.getInstance();
    private final PasswordValidator passwordValidator = PasswordValidator.getInstance();

    private ClientService() {
    }

    public static ClientService getInstance(){
        return INSTANCE;
    }
    public List<Optional<Order>> getOrderHistory(Customer customer) {
        return orderDao.customerOrders(customer.getCustomerId());
    }

    public List<Optional<Order>> getAllOrders(Customer customer) {
        return orderDao.findAllByCustomerId(customer.getCustomerId());
    }

    public List<Optional<Order>> getCurrentOrders(Customer customer) {
        return orderDao.currentCustomerOrders(customer.getCustomerId());
    }

    public Map<Order, List<Optional<Order>>> showOrdersSearch(Customer customer, Integer orderId, LocalDateTime orderDateFrom, LocalDateTime orderDateTo, List<String> statusList) {
        List<Optional<Order>> temp = orderDao.findAllByCustomerId(customer.getCustomerId()).stream()
                .filter(value -> value.get().getDate().isAfter(orderDateFrom) && value.get().getDate().isBefore(orderDateTo))
                .filter(value -> statusList.contains(value.get().getStatus()))
                .toList();
        List<Optional<Order>> temp1;

        var result = new HashMap<Order, List<Optional<Order>>>();

        if (orderId != null) {
            temp1 = temp.stream().filter(value -> value.get().getOrderId().equals(orderId))
                    .toList();
        } else {
            temp1 = temp;
        }
        temp1.stream().distinct().forEach(value -> {
            if (result.get(value.get()) == null) {
                            result.put(value.get(), new ArrayList<Optional<Order>>());
                            result.get(value.get()).add(value);
                            } else {
                                result.get(value.get()).add(value);
                            }
                        });
        return result;
        }

    public List<FreighterRoutes> getAvailableRoutes() {
        List<FreighterRoutes> cities = freighterRoutesDao.findAll();
        return cities;
    }
    public List<Freighter> getAvailableFreighters(String city) {
        List<Freighter> availableFreighters = freighterDao.getAvailableFreightersByDirection(city);
        return availableFreighters;
    }
    public CreateCargoDto cargoByOrderId(Integer id) {
        return orderDao.CargoByOrderId(id);
    }

    public void completeOrder(Integer orderId){
        
    }

    public Optional<Route> findByRouteName(String city){
        return routeDao.findByName(city);
    }
    public Optional<Order> findByOrderId(Integer id){
        return orderDao.findById(id);
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

    public void updateOrder(CreateCargoDto cargoDto, Freighter freighter, Customer customer, String destination, Order order) {
        Cargo curCargo = Cargo.builder()
                .cargoSize(cargoDto.getCargoSize())
                .cargoWeight(cargoDto.getCargoWeight())
                .isFragile(cargoDto.getIsFragile())
                .destination(destination)
                .freighter(freighter)
                .order(order)
                .customer(customer)
                .build();
        cargoDao.update(curCargo);
    }

    public void checkPassword(ShowCustomerDto dto){
        var result = passwordValidator.isValid(dto);
        if(!result.isValid()){
            throw new ValidationException(result.getErrors());
        }
    }

    public void updatePassword(ShowCustomerDto dto) {
        customerDao.updatePassword(dto.getLogin(), dto.getPassword());
    }
}