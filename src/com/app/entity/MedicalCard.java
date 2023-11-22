package com.app.entity;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public final class MedicalCard {
    private String medSerialNumber;
    private Boolean hivStatus;
    private String illness;
}