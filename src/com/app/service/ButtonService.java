package com.app.service;

import com.app.dao.*;
import com.app.dto.ShowCustomerDto;
import com.app.dto.FreighterDto;
import com.app.dto.ShowWorkerDto;
import com.app.entity.Position;
import com.app.entity.VoyageLog;
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

    public List<Position> getAllPositions(){
        return positionDao.findAll();
    }

    public List<VoyageLog> getVoyageLog(){
        return voyageLogDao.findAll().stream()
                .distinct()
                .toList();
    }
}
