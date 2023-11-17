package com.app.exceptions;

import com.app.util.PropertiesUtil;

import java.util.Properties;

public class FailedToLoadPropertiesFileException extends RuntimeException{
    public FailedToLoadPropertiesFileException(String message){
        super(message);

    }

}
