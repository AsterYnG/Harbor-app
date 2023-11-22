package com.app.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ShowCustomerDto {
    String fullName;
    String email;
    String password;
    String login;
}
