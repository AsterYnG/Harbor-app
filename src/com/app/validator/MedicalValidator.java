package com.app.validator;

import com.app.dao.MedicalCardDao;
import com.app.dto.CreateMedicalCardDto;

public class MedicalValidator implements Validator<CreateMedicalCardDto>{
    private static final MedicalValidator INSTANCE = new MedicalValidator();
    private final MedicalCardDao medicalCardDao = MedicalCardDao.getInstance();
    public static MedicalValidator getInstance(){
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(CreateMedicalCardDto dto) {
        var validationResult = new ValidationResult();

        if(medicalCardDao.findById(dto.getMedSerialNumber()).isPresent()){
            validationResult.add(Error.of("invalid.MedicalCard.SerialNumber","Серия и номер уже существуют"));
        }


        return validationResult;
    }
}
