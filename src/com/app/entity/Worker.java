package com.app.entity;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

@Data
@Builder
public final class Worker {
    private Integer workerId;
    private Timestamp hiringDate;
    private Position position;
    private MedicalCard medSerialNumber;
    private Passport passportSerialNumber;
    private Education educationSerialNumber;
    private Employment employmentSerialNumber;
    private Dock dockId;
}