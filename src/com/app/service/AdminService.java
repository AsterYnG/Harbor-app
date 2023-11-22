package com.app.service;

import com.app.dao.CustomerDao;

public class AdminService {
    private static final AdminService INSTANCE = new AdminService();

    private AdminService() {
    }

    public static AdminService getInstance() {
        return INSTANCE;
    }

    private final CustomerDao customerDao = CustomerDao.getInstance();


}
