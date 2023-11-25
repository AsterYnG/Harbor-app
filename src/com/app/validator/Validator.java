package com.app.validator;

public interface Validator <T>{
    ValidationResult isValid(T dto);
}
