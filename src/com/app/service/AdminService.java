package com.app.service;

import com.app.dao.CustomerDao;
import com.app.dto.*;

public class AdminService {
    private static final AdminService INSTANCE = new AdminService();

    private AdminService() {
    }

    public static AdminService getInstance() {
        return INSTANCE;
    }

    private final CustomerDao customerDao = CustomerDao.getInstance();

    public void saveWorker(CreateWorkerDto worker, CreateEducationDto educationDto, CreateEmploymentDto employment, CreatePassportDto passport, CreateMedicalCardDto medicalCard){

    }


}
