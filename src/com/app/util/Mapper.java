package com.app.util;

import com.app.dto.ShowCustomerDto;
import com.app.dto.FreighterDto;
import com.app.dto.ShowWorkerDto;
import com.app.entity.Customer;
import com.app.entity.Freighter;
import com.app.entity.Worker;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Mapper {
    public static ShowWorkerDto mapFromWorkerToDto(Worker worker){
        return ShowWorkerDto.builder()
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

    public static ShowCustomerDto mapFromCustomerToDto(Customer customer){
        return ShowCustomerDto.builder()
                .login(customer.getLogin())
                .password(customer.getPassword())
                .email(customer.getEmail())
                .fullName(customer.getFullName())
                .build();
    }

}
