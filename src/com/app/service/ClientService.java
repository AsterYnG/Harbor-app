package com.app.service;

import com.app.dao.OrderDao;
import com.app.entity.Customer;
import com.app.entity.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClientService {
    private static final ClientService INSTANCE = new ClientService();

    private ClientService() {
    }

    public static ClientService getInstance(){
        return INSTANCE;
    }
    public final List<Optional<Order>> getOrderHistory(Customer customer) {
        List<Optional<Order>> clientOrders = OrderDao.getInstance().customerOrders(customer.getCustomerId());
        return clientOrders;
    }

    public List<String> getCurrentOrders(Integer clientId) {
        // добавить логику на получение текущих заказов из БД
        return Arrays.asList("Текущий заказ #1", "Текущий заказ #2");
    }

    public String createNewOrder(String clientId, String orderDetails) {
        // добавить логику на создание новых заказов
        return "Новый заказ создан: " + orderDetails;
    }

    public String editClientProfile(String clientId, String newProfileData) {
        // добавить логику на редактирование профиля клиента
        return "Профиль клиента отредактирован: " + newProfileData;
    }
}