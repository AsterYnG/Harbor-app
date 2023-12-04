package com.app.service;

import com.app.dao.*;
import com.app.dto.ShowCustomerDto;
import com.app.dto.FreighterDto;
import com.app.dto.ShowWorkerDto;
import com.app.entity.*;
import com.app.util.Mapper;

import java.util.List;

public class ButtonService {
    private static final ButtonService INSTANCE = new ButtonService();

    private final WorkerDao workerDao = WorkerDao.getInstance();
    private final FreighterDao freighterDao = FreighterDao.getInstance();
    private final CustomerDao customerDao = CustomerDao.getInstance();

    private final PositionDao positionDao = PositionDao.getInstance();
    private final VoyageLogDao voyageLogDao = VoyageLogDao.getInstance();

    private ButtonService() {
    }

    public static ButtonService getInstance() {
        return INSTANCE;
    }

    public List<Worker> showAllEmployees(){

        return workerDao.findAll().stream().distinct().toList();
    }

    public List<Freighter> showAllFreighters(){
        return freighterDao.findAll().stream().distinct().toList();
    }
    public List<Customer> showAllCustomers(){
        return customerDao.findAll().stream().distinct().toList();

    }

    public List<Position> getAllPositions(){
        return positionDao.findAll();
    }

    public List<VoyageLog> getVoyageLog(){
        return voyageLogDao.findAll().stream()
                .distinct()
                .toList();
    }
}
