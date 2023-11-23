package com.app.util;

import com.app.dto.*;
import com.app.entity.*;
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

    public static Passport mapFromDtoToPassport(CreatePassportDto passportDto){ // РЕГИСТРАЦИЯ добавить!
        return Passport.builder()
                .sex(passportDto.getSex())
                .birthDate(passportDto.getBirthDate())
                .citizenship(passportDto.getCitizenship())
                .fullName(passportDto.getFullName())
                .build();
    }

    public static Registration mapFromDtoToRegistration(CreateRegistrationDto registrationDto){
        return Registration.builder()
                .street(registrationDto.getStreet())
                .region(registrationDto.getRegion())
                .house(registrationDto.getHouse())
                .flat(registrationDto.getFlat())
                .city(registrationDto.getCity())
                .build();
    }

    public static Worker mapFromDtoToWorker(CreateWorkerDto workerDto){
        return Worker.builder()
                .hiringDate(workerDto.getHiringDate())
                .build();
    }

    public static MedicalCard mapFromDtoToMedicalCard(CreateMedicalCardDto medicalCardDto){
        return MedicalCard.builder()
                .hivStatus(medicalCardDto.getHivStatus())
                .illness(medicalCardDto.getIllness())
                .medSerialNumber(medicalCardDto.getMedSerialNumber())
                .build();
    }

    public static Education mapFromDtoToEducation(CreateEducationDto educationDto){
        return Education.builder()
                .educationSerialNumber(educationDto.getEducationSerialNumber())
                .establishment(educationDto.getEstablishment())
                .grade(educationDto.getGrade())
                .build();
    }

    public static Employment mapFromDtoToEmployment(CreateEmploymentDto employmentDto){
        return Employment.builder()
                .employmentSerialNumber(employmentDto.getEmploymentSerialNumber())
                .experience(employmentDto.getExperience())
                .previousJob(employmentDto.getPreviousJob())
                .build();
    }

}
