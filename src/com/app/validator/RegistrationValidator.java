package com.app.validator;

import com.app.dao.PassportDao;
import com.app.dto.CreateRegistrationDto;

import java.time.LocalDate;

public class RegistrationValidator implements Validator<CreateRegistrationDto>{
    private static final RegistrationValidator INSTANCE = new RegistrationValidator();
    @Override
    public ValidationResult isValid(CreateRegistrationDto dto) {
        var validationResult = new ValidationResult();

        if(dto.getRegion().isEmpty()){
            validationResult.add(Error.of("invalid.Registration.region","Заполните поле регион"));
        }
        if(dto.getCity().isEmpty()){
            validationResult.add(Error.of("invalid.Registration.city","Заполните поле город"));
        }
        if(dto.getStreet().isEmpty()){
            validationResult.add(Error.of("invalid.Registration.street","Заполните поле улица"));
        }
        if(dto.getHouse().isEmpty()){
            validationResult.add(Error.of("invalid.Registration.house","Заполните поле дом"));
        }
        if(dto.getFlat().toString().isEmpty()){
            validationResult.add(Error.of("invalid.Registration.flat","Заполните поле квартира"));
        }


        return validationResult;
    }

    public static RegistrationValidator getInstance(){
        return INSTANCE;
    }


}
