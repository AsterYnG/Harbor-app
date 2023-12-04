package com.app.validator;

import com.app.dao.PassportDao;
import com.app.dto.CreatePassportDto;

import java.time.LocalDate;

public class PassportValidator implements Validator<CreatePassportDto>{

    private final PassportDao passportDao = PassportDao.getInstance();
    private static final PassportValidator INSTANCE = new PassportValidator();
    @Override
    public ValidationResult isValid(CreatePassportDto dto) {
        var validationResult = new ValidationResult();

        if(passportDao.findById(dto.getPassportSerialNumber()).isPresent()){
            validationResult.add(Error.of("invalid.Passport.SerialNumber","Серия и номер паспорта уже существуют"));
        }
        if((dto.getBirthDate().getYear() > LocalDate.now().getYear() - 18) || (dto.getBirthDate().getYear() < LocalDate.now().getYear() - 100)){
            validationResult.add(Error.of("invalid.Passport.birthDate","Неверно указана дата, проверьте правильность"));
        }
        if(dto.getSex().isEmpty()){
            validationResult.add(Error.of("invalid.Passport.sex","Не выбран пол"));
        }
        if(dto.getCitizenship().isEmpty()){
            validationResult.add(Error.of("invalid.Passport.citizenship","Не выбрана национальность"));
        }


        return validationResult;
    }

    public ValidationResult isValid(String passportSerialNumber) {
        var validationResult = new ValidationResult();

        if(passportDao.findById(passportSerialNumber).isEmpty()){
            validationResult.add(Error.of("invalid.Passport.SerialNumber","Сотрудника с таким паспортом нет"));
        }

        return validationResult;
    }

    public static PassportValidator getInstance(){
        return INSTANCE;
    }

}
