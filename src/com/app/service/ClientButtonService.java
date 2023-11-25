package com.app.service;

import com.app.dao.*;
import com.app.dto.ShowCustomerDto;
import com.app.dto.FreighterDto;
import com.app.dto.ShowWorkerDto;
import com.app.entity.FreighterRoutes;
import com.app.entity.Position;
import com.app.entity.Route;
import com.app.util.Mapper;

import java.util.List;

public class ClientButtonService {
    private static final ClientButtonService INSTANCE = new ClientButtonService();

    private final WorkerDao workerDao = WorkerDao.getInstance();
    private final FreighterRoutesDao freighterRoutesDao = FreighterRoutesDao.getInstance();
    private final FreighterDao freighterDao = FreighterDao.getInstance();

    private final RouteDao routeDao = RouteDao.getInstance();
    private final CustomerDao customerDao = CustomerDao.getInstance();

    private final PositionDao positionDao = PositionDao.getInstance();

    private ClientButtonService() {
    }

    public static ClientButtonService getInstance() {
        return INSTANCE;
    }

    public List<Route> getAllRoutes(){
        return routeDao.findAll();
    }
}
