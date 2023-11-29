package com.app.validator;

import com.app.dto.ShowCustomerDto;
import com.app.dao.CustomerDao;

import java.time.LocalDate;

public class PasswordValidator implements Validator<ShowCustomerDto>{

    private final CustomerDao customerDao = CustomerDao.getInstance();
    private static final PasswordValidator INSTANCE = new PasswordValidator();
    @Override
    public ValidationResult isValid(ShowCustomerDto dto) {
        var validationResult = new ValidationResult();
        if(customerDao.findByPassword(dto.getLogin(),dto.getPassword()).isEmpty()){
            validationResult.add(Error.of("invalid.OldPassword","Неверно введён старый пароль!"));
        }
        return validationResult;
    }
    public static PasswordValidator getInstance(){
        return INSTANCE;
    }

}