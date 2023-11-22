package com.app.service;

import com.app.dao.CustomerDao;
import com.app.entity.Customer;

import java.sql.SQLException;

public class RegistrationService {
    private static final RegistrationService INSTANCE = new RegistrationService();

    private RegistrationService() {
    }

    public static RegistrationService getInstance(){
        return INSTANCE;
    }

    private final CustomerDao customerDao = CustomerDao.getInstance();

    public void save(Customer customer)  {
        customerDao.save(customer);
    }
    public boolean checkUniqueLogin(String login){
        return customerDao.findByLogin(login).isPresent();
    }

    public boolean checkUniqueEmail(String email){
        return customerDao.findByEmail(email).isPresent();
    }


}
