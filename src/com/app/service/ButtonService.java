package com.app.service;

import com.app.dao.FreighterDao;
import com.app.dao.WorkerDao;
import com.app.dto.FreighterDto;
import com.app.dto.WorkerDto;
import com.app.entity.Freighter;
import com.app.entity.Worker;
import com.app.util.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class ButtonService {
    private static final ButtonService INSTANCE = new ButtonService();

    private final WorkerDao workerDao = WorkerDao.getInstance();
    private final FreighterDao freighterDao = FreighterDao.getInstance();

    private ButtonService() {
    }

    public static ButtonService getInstance() {
        return INSTANCE;
    }

    public List<WorkerDto> showAllEmployees(){

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
}
