package com.app.service;

import com.app.dao.*;
import com.app.dto.*;
import com.app.entity.*;
import com.app.exceptions.ValidationException;
import com.app.util.Mapper;
import com.app.validator.*;
import org.eclipse.tags.shaded.org.apache.bcel.generic.LUSHR;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
    private final CargoDao cargoDao = CargoDao.getInstance();
    private final ShipDao shipDao = ShipDao.getInstance();
    private final TeamMemberDao teamMemberDao = TeamMemberDao.getInstance();
    private final OrderDao orderDao = OrderDao.getInstance();


    private final FreighterDao freighterDao = FreighterDao.getInstance();
    private final RouteDao routeDao = RouteDao.getInstance();
    private final FreighterRoutesDao freighterRoutesDao = FreighterRoutesDao.getInstance();

    private final PassportValidator passportValidator = PassportValidator.getInstance();
    private final RegistrationValidator registrationValidator = RegistrationValidator.getInstance();
    private final MedicalValidator medicalValidator = MedicalValidator.getInstance();
    private final EducationValidator educationValidator = EducationValidator.getInstance();
    private final EmploymentValidator employmentValidator = EmploymentValidator.getInstance();
    private final RouteValidator routeValidator = RouteValidator.getInstance();


    public void saveWorker(CreateEducationDto educationDto, CreateEmploymentDto employmentDto, CreatePassportDto passportDto, CreateRegistrationDto registrationDto, CreateMedicalCardDto medicalCardDto, Position position) {
        Dock dock;

        if (allowedPositions.contains(position.getPosition())) {
            dock = dockDao.findLeastWorkersDock();
        } else {
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

    public Optional<Position> findByPositionName(String position) {
        return positionDao.findByName(position);
    }


    public void saveFreighter(Freighter freighter) {
        freighterDao.save(freighter);
    }

    public void checkPassport(CreatePassportDto dto) {
        var result = passportValidator.isValid(dto);
        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }
    }

    public void checkRegistration(CreateRegistrationDto dto) {
        var result = registrationValidator.isValid(dto);
        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }
    }

    public void checkMedicalCard(CreateMedicalCardDto dto) {
        var result = medicalValidator.isValid(dto);
        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }
    }

    public void checkEducationCard(CreateEducationDto dto) {
        var result = educationValidator.isValid(dto);
        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }
    }

    public void checkEmploymentCard(CreateEmploymentDto dto) {
        var result = employmentValidator.isValid(dto);
        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }
    }

    public void checkRoute(Route route) {
        var result = routeValidator.isValid(route);
        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }
    }

    public void saveRoute(Route route, List<Freighter> freighters) {
        Route saved = routeDao.save(route);

        for (Freighter freighter : freighters) {
            FreighterRoutes build = FreighterRoutes.builder()
                    .route(route)
                    .freighter(freighter)
                    .build();
            freighterRoutesDao.save(build);
        }

    }

    public List<Freighter> getFreighters() {
        return freighterDao.findAll();

    }

    public List<Route> getAvailableRoutes() {
        return routeDao.findAll();
    }

    public List<FreighterRoutes> getFilteredRoutes(List<Freighter> freighters, List<Route> routes, Integer durationFrom, Integer durationTo) {
        List<FreighterRoutes> result = new ArrayList<>();
        for (Freighter freighter : freighters) {
            FreighterRoutes freighterRoute = FreighterRoutes.builder()
                    .freighter(freighter)
                    .build();
            for (Route route : routes) {
                freighterRoute.setRoute(route);
                result.addAll(freighterRoutesDao.findFiltered(freighterRoute, durationFrom, durationTo));
            }
        }
        return result.stream()
                .distinct()
                .toList();
    }

    public List<Cargo> getFilteredCargos(Integer weightFrom, Integer weightTo, Integer sizeFrom, Integer sizeTo, Boolean isFragile, Boolean allCargos, List<Integer> customer, List<String> routes, List<String> freighters) {


        List<Cargo> temp = cargoDao.findAll();
        List<Cargo> result = temp.stream()
                .filter(value -> value.getCargoWeight() > weightFrom && value.getCargoWeight() < weightTo)
                .filter(value -> value.getCargoSize() > sizeFrom && value.getCargoSize() < sizeTo)
                .filter(value -> customer.contains(value.getCustomer().getCustomerId()))
                .filter(value -> routes.contains(value.getDestination()))
                .filter(value -> freighters.contains(value.getFreighter().getFreighterName()))
                .toList();
        if (allCargos) {
            return result;
        } else {
            return result.stream().filter(value -> value.getIsFragile().equals(isFragile)).toList();
        }
    }

    public List<Customer> getAllClients() {
        return customerDao.findAll();
    }

    public List<Ship> getAllShips() {
        return shipDao.findAll();

    }

    public List<TeamMember> getAllTeamMembers() {
        return teamMemberDao.findAll();
    }

    public List<Order> getAllOrders() {
        return orderDao.findAll();
    }

    public List<Position> getAllPositions() {
        return positionDao.findAll();
    }

    public List<Freighter> getFilteredFreighters(Integer weightFrom, Integer weightTo, Integer sizeFrom, Integer sizeTo, Integer taxFrom, Integer taxTo, Integer fragileFrom, Integer fragileTo, List<Freighter> freighters) {
        return getFreighters().stream()
                .filter(value -> value.getWeightCost() > weightFrom && value.getWeightCost() < weightTo)
                .filter(value -> value.getSizeCost() > sizeFrom && value.getSizeCost() < sizeTo)
                .filter(value -> value.getFragileCost() > fragileFrom && value.getFragileCost() < fragileTo)
                .filter(freighters::contains)
                .toList();
    }

    public List<Ship> getFilteredShips(Integer shipSizeFrom, Integer shipSizeTo, Integer shipCapacityFrom, Integer shipCapacityTo, Boolean allShips, Boolean inUse, List<Freighter> freighters, List<Ship> ships, List<Team> teams) {
        var temp = getAllShips().stream()
                .filter(value -> value.getShipModel().getShipSize() > shipSizeFrom && value.getShipModel().getShipSize() < shipSizeTo)
                .filter(value -> value.getShipModel().getShipCapacity() > shipCapacityFrom && value.getShipModel().getShipCapacity() < shipCapacityTo)
                .filter(value -> freighters.contains(value.getFreighter()))
                .filter(value -> teams.contains(value.getTeam()))
                .filter(ships::contains)
                .toList();

        if (allShips) {
            return temp;
        } else {
            return temp.stream().filter(value -> value.getInUse().equals(inUse))
                    .toList();
        }
    }

    public List<Team> getFilteredTeams(Integer experienceFrom, Integer experienceTo, List<String> fullNames, List<String> citizenships) {
        return getAllTeamMembers().stream()
                .filter(value -> fullNames.contains(value.getFullName()))
                .filter(value -> citizenships.contains(value.getCitizenship()))
                .filter(value -> value.getTeam().getExperience() > experienceFrom && value.getTeam().getExperience() < experienceTo)
                .map(TeamMember::getTeam)
                .distinct()
                .toList();
    }

    public List<Customer> getFilteredCustomers(String fullName, String email, String login) {
        var temp = getAllClients();
        List<Customer> temp1;
        List<Customer> temp2;
        List<Customer> temp3;
        if (!fullName.isEmpty()) {
            temp1 = temp.stream().filter(value -> value.getFullName().equals(fullName)).toList();
        } else {
            temp1 = temp;
        }
        if (!email.isEmpty()) {
            temp2 = temp1.stream().filter(value -> value.getEmail().equals(email)).toList();
        } else {
            temp2 = temp1;
        }
        if (!login.isEmpty()) {
            temp3 = temp2.stream().filter(value -> value.getLogin().equals(login)).toList();
        } else {
            temp3 = temp2;
        }
        return temp3;
    }

    public Map<Order, List<Cargo>> getFilteredOrders(Integer clientId, Integer orderId, String fullName, LocalDateTime from, LocalDateTime to, List<String> statusList) {
        List<Cargo> temp = cargoDao.findAll().stream()
                .filter(value -> value.getOrder().getDate().isAfter(from) && value.getOrder().getDate().isBefore(to))
                .filter(value -> statusList.contains(value.getOrder().getStatus()))
                .toList();
        List<Cargo> temp1;
        List<Cargo> temp2;
        List<Integer> temp3;
        var result = new HashMap<Order, List<Cargo>>();

        if (clientId != null) {
            temp1 = temp.stream().filter(value -> value.getCustomer().getCustomerId().equals(clientId))
                    .toList();
        } else {
            temp1 = temp;
        }
        if (orderId != null) {
            temp2 = temp1.stream().filter(value -> value.getOrder().getOrderId().equals(orderId))
                    .toList();
        } else {
            temp2 = temp1;
        }
        if (!fullName.isBlank()) {

            temp2.stream().filter(value -> value.getCustomer().getFullName().equals(fullName))
                    .distinct()
                    .forEach(value -> {
                        if (result.get(value.getOrder()) == null) {
                            result.put(value.getOrder(), new ArrayList<Cargo>());
                            result.get(value.getOrder()).add(value);
                        } else {
                            result.get(value.getOrder()).add(value);
                        }
                    });

        } else {
            temp2.stream().distinct()
                    .forEach(value -> {
                        if (result.get(value.getOrder()) == null) {
                            result.put(value.getOrder(), new ArrayList<Cargo>());
                            result.get(value.getOrder()).add(value);
                        } else {
                            result.get(value.getOrder()).add(value);
                        }
                    });
        }
        return result;
    }

    public List<Worker> getFilteredWorkers(Integer dockId, Integer ageFrom, Integer ageTo, Integer salaryFrom, Integer salaryTo, String passportSerialNumber, String fullName, LocalDate hiringDateFrom, LocalDate hiringDateTo, List<Position> positions) {
        List<Worker> temp = workerDao.findAll().stream()
                .distinct()
                .filter(value -> value.getHiringDate().isAfter(hiringDateFrom) && value.getHiringDate().isBefore(hiringDateTo))
                .filter(value -> (LocalDate.now().getYear() - value.getPassportSerialNumber().getBirthDate().getYear()) > ageFrom && (LocalDate.now().getYear() - value.getPassportSerialNumber().getBirthDate().getYear()) < ageTo)
                .filter(value -> value.getPosition().getSalary() > salaryFrom && value.getPosition().getSalary() < salaryTo)
                .filter(value -> positions.contains(value.getPosition()))
                .toList();
        List<Worker> temp1;
        List<Worker> temp2;
        if (!passportSerialNumber.isBlank()) {
            temp1 = temp.stream().filter(value -> value.getPassportSerialNumber().getPassportSerialNumber().equals(passportSerialNumber)).toList();
        } else temp1 = temp;

        if (!fullName.isBlank()) {
            temp2 = temp1.stream().filter(value -> value.getPassportSerialNumber().getFullName().equals(fullName)).toList();
        } else temp2 = temp;
        if (dockId != null) {
            return temp2.stream().filter(value ->{
                if (value.getDockId().getDockId() != null){
               return value.getDockId().getDockId().equals(dockId);
                }
                else return false;
                    } )
                    .toList();
        } else return temp2;
    }


}
