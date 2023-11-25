package com.app.exceptions;

import lombok.Getter;
import com.app.validator.Error;
import java.util.List;

public class ValidationException extends RuntimeException {
    @Getter
    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
    public Error findError(String code){
        for (var error:errors) {
            if (error.getCode().equals(code)) return error;
        }
        return null;
    }
}
