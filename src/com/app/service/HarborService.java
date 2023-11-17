package com.app.service;

import com.app.dao.FreighterDao;
import com.app.dto.FreighterDto;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class HarborService {
    private static final HarborService INSTANCE = new HarborService();

    private HarborService() {
    }

    private final FreighterDao freighterDao = FreighterDao.getInstance();

    public List<FreighterDto> findAll(){
        return freighterDao.findAll().stream()
                .map(freighter -> FreighterDto.builder()
                        .freighterId(freighter.getFreighterId())
                        .freighterName(freighter.getFreighterName())
                        .weightCost(freighter.getWeightCost())
                        .build())
                .toList();
    }

    public static HarborService getInstance() {
        return INSTANCE;
    }
}
