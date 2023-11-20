package com.app.entity;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public final class MedicalCard {
    private Integer medSerialNumber;
    private Boolean hivStatus;
    private String illness;
}