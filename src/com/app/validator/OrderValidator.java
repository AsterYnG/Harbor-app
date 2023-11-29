package com.app.validator;

public class OrderValidator {

    public static final OrderValidator INSTANCE = new OrderValidator();
    public static OrderValidator getInstance(){
        return INSTANCE;
    }
}
