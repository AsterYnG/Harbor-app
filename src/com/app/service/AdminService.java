package com.app.service;

import com.app.dao.*;
import com.app.dto.*;
import com.app.entity.*;
import com.app.util.Mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminService {
    private static final AdminService INSTANCE = new AdminService();

    private final List<String> allowedPositions = new ArrayList<>();

    private AdminService() {
    }

    public static AdminService getInstance() {
        return INSTANCE;
    }

    {
        allowedPositions.add("Loader");
        allowedPositions.add("Cleaner");
    }
    private final CustomerDao customerDao = CustomerDao.getInstance();

    private final PositionDao positionDao = PositionDao.getInstance();
    private final DockDao dockDao = DockDao.getInstance();
    private final EmploymentDao employmentDao = EmploymentDao.getInstance();
    private final MedicalCardDao medicalCardDao = MedicalCardDao.getInstance();
    private final RegistrationDao registrationDao = RegistrationDao.getInstance();
    private final PassportDao passportDao = PassportDao.getInstance();
    private final EducationDao educationDao = EducationDao.getInstance();
    private final WorkerDao workerDao = WorkerDao.getInstance();


    public void saveWorker(CreateEducationDto educationDto, CreateEmploymentDto employmentDto, CreatePassportDto passportDto, CreateRegistrationDto registrationDto , CreateMedicalCardDto medicalCardDto, Position position){
        Dock dock;

        if(allowedPositions.contains(position.getPosition())){
            dock = dockDao.findLeastWorkersDock();
        }
        else{
            dock = null;
        }
        var education = Mapper.mapFromDtoToEducation(educationDto);
        var employment = Mapper.mapFromDtoToEmployment(employmentDto);
        var medicalCard = Mapper.mapFromDtoToMedicalCard(medicalCardDto);
        var registration = Mapper.mapFromDtoToRegistration(registrationDto);
        var passport = Passport.builder()
                .regId(registration)
                .fullName(passportDto.getFullName())
                .citizenship(passportDto.getCitizenship())
                .birthDate(passportDto.getBirthDate())
                .sex(passportDto.getSex())
                .passportSerialNumber(passportDto.getPassportSerialNumber())
                .build();
        var passportTemp = Mapper.mapFromDtoToPassport(passportDto);

        var worker = Worker.builder()
                .hiringDate(LocalDate.now())
                .dockId(dock)
                .educationSerialNumber(education)
                .employmentSerialNumber(employment)
                .medSerialNumber(medicalCard)
                .passportSerialNumber(passport)
                .position(position)
                .build();

        worker.getPassportSerialNumber().setRegId(registrationDao.save(worker.getPassportSerialNumber().getRegId()));
        worker.setPassportSerialNumber(passportDao.save(worker.getPassportSerialNumber()));
        worker.setEmploymentSerialNumber(employmentDao.save(worker.getEmploymentSerialNumber()));
        worker.setMedSerialNumber(medicalCardDao.save(worker.getMedSerialNumber()));
        worker.setEducationSerialNumber(educationDao.save(worker.getEducationSerialNumber()));

        workerDao.save(worker);


    }

    public Optional<Position> findByPositionName(String position){
        return positionDao.findByName(position);
    }

}
