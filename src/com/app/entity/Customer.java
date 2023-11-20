package com.app.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
    private String fullName;
    private String email;
    private Integer customerId;
    private String password;
    private String login;

}
