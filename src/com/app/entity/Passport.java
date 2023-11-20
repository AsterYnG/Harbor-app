package com.app.entity;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

@Data
@Builder
public final class Passport {
    private Integer passportSerialNumber;
    private String fullName;
    private Timestamp birthDate;
    private String sex;
    private String citizenship;
    private Registration regId;
}
