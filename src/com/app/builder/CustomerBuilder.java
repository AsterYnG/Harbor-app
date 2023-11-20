package com.app.builder;

import com.app.entity.Customer;
import com.app.exceptions.NoSuchColumnInResultSetException;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;

@UtilityClass
public final class CustomerBuilder {
    public Customer buildCustomer(ResultSet resultSet){
        try {
            return Customer.builder()
                    .customerId(resultSet.getObject("customer_id",Integer.class))
                    .fullName(resultSet.getObject("full_name",String.class))
                    .password(resultSet.getObject("password",String.class))
                    .login(resultSet.getObject("login",String.class))
                    .email(resultSet.getObject("email",String.class))
                    .build();
        } catch (SQLException e) {
            throw new NoSuchColumnInResultSetException(e);
        }
    }

}
