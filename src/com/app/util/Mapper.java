package com.app.util;

import com.app.dto.FreighterDto;
import com.app.dto.WorkerDto;
import com.app.entity.Freighter;
import com.app.entity.Worker;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Mapper {
    public static WorkerDto mapFromWorkerToDto(Worker worker){
        return WorkerDto.builder()
                .fullName(worker.getPassportSerialNumber().getFullName())
                .educationSerialNumber(worker.getEducationSerialNumber().getEducationSerialNumber())
                .medSerialNumber(worker.getMedSerialNumber().getMedSerialNumber())
                .passportSerialNumber(worker.getPassportSerialNumber().getPassportSerialNumber())
                .employmentSerialNumber(worker.getEmploymentSerialNumber().getEmploymentSerialNumber())
                .hiringDate(worker.getHiringDate())
                .position(worker.getPosition().getPosition())
                .build();
    }
    public static FreighterDto mapFromFreighterToDto(Freighter freighter){
        return FreighterDto.builder()
                .freighterName(freighter.getFreighterName())
                .tax(freighter.getTax())
                .fragileCost(freighter.getFragileCost())
                .sizeCost(freighter.getSizeCost())
                .weightCost(freighter.getWeightCost())
                .build();
    }
}
