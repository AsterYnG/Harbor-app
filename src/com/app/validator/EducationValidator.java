package com.app.validator;

import com.app.dao.EducationDao;
import com.app.dto.CreateEducationDto;

public class EducationValidator implements Validator<CreateEducationDto>{
    private static final EducationValidator INSTANCE = new EducationValidator();
    public static EducationValidator getInstance(){
        return INSTANCE;
    }
    private final EducationDao educationDao = EducationDao.getInstance();
    @Override
    public ValidationResult isValid(CreateEducationDto dto) {
        var result = new ValidationResult();

        if (educationDao.findById(dto.getEducationSerialNumber()).isPresent()){
            result.add(Error.of("invalid.Education.serialNumber","Номер трудовой книжки уже существует"));
        }

        return result;
    }
}
