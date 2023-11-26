package com.app.validator;

import com.app.dao.RouteDao;
import com.app.entity.Route;

public class RouteValidator implements Validator<Route>{
    private static final RouteValidator INSTANCE = new RouteValidator();
    private final RouteDao routeDao = RouteDao.getInstance();

    public static RouteValidator getInstance(){
        return INSTANCE;
    }
    @Override
    public ValidationResult isValid(Route route) {
        var result = new ValidationResult();

        if(routeDao.findByNameAndCity(route.getDestinationCity(),route.getDestinationCountry()).isPresent()){
            result.add(Error.of("invalid.Route.countryAndCity","Данный путь уже существует"));
        }


        return result;
    }
}
