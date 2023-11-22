package com.app.entity;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public final class Education {
    private String educationSerialNumber;
    private String grade;
    private String establishment;
}
