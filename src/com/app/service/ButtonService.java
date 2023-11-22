package com.app.service;

import com.app.dao.CustomerDao;
import com.app.dao.FreighterDao;
import com.app.dao.WorkerDao;
import com.app.dto.ShowCustomerDto;
import com.app.dto.FreighterDto;
import com.app.dto.ShowWorkerDto;
import com.app.util.Mapper;

import java.util.List;

public class ButtonService {
    private static final ButtonService INSTANCE = new ButtonService();

    private final WorkerDao workerDao = WorkerDao.getInstance();
    private final FreighterDao freighterDao = FreighterDao.getInstance();
    private final CustomerDao customerDao = CustomerDao.getInstance();

    private ButtonService() {
    }

    public static ButtonService getInstance() {
        return INSTANCE;
    }

    public List<ShowWorkerDto> showAllEmployees(){

        return workerDao.findAll().stream()
                .map(Mapper::mapFromWorkerToDto)
                .distinct()
                .toList();
    }

    public List<FreighterDto> showAllFreighters(){
        return freighterDao.findAll().stream()
                .map(Mapper::mapFromFreighterToDto)
                .distinct()
                .toList();
    }
    public List<ShowCustomerDto> showAllCustomers(){
        return customerDao.findAll().stream()
                .map(Mapper::mapFromCustomerToDto)
                .distinct()
                .toList();
    }
}
