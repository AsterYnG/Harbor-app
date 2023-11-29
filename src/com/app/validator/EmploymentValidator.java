package com.app.validator;

import com.app.dao.EmploymentDao;
import com.app.dto.CreateEmploymentDto;

public class EmploymentValidator implements Validator<CreateEmploymentDto>{
    private static final EmploymentValidator INSTANCE = new EmploymentValidator();
    private final EmploymentDao employmentDao = EmploymentDao.getInstance();
    public static EmploymentValidator getInstance(){
        return INSTANCE;
    }
    @Override
    public ValidationResult isValid(CreateEmploymentDto dto) {
        var validationResult = new ValidationResult();

        if (employmentDao.findById(dto.getEmploymentSerialNumber()).isPresent()){
            validationResult.add(Error.of("invalid.Employment.serialNumber","Серия и номер уже существуют"));
        }


        return validationResult;
    }
}
