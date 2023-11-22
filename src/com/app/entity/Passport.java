package com.app.entity;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
public final class Passport {
    private String passportSerialNumber;
    private String fullName;
    private LocalDate birthDate;
    private String sex;
    private String citizenship;
    private Registration regId;
}
