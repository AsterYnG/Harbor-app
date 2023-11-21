package com.app.service;

import com.app.dao.CustomerDao;
import com.app.entity.Customer;

import java.util.Optional;

public class LoginService {
    private static final LoginService INSTANCE = new LoginService();

    private LoginService() {
    }

    public static LoginService getInstance() {
        return INSTANCE;
    }

    private final CustomerDao customerDao = CustomerDao.getInstance();

    public final boolean checkUserData(Customer customer) {
        Optional<Customer> DataBaseCustomer = customerDao.findByLogin(customer.getLogin());
        return DataBaseCustomer.filter(value -> customer.getPassword().equals(value.getPassword())).isPresent();
    }
}
